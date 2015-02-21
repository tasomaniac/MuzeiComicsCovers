package com.tasomaniac.muzei.comiccovers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.tasomaniac.muzei.comiccovers.model.Comic;

import java.util.Random;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class ComicVineArtSource extends RemoteMuzeiArtSource {
    private static final String SOURCE_NAME = "ComicVineArtSource";
    public static final String NUM_OF_TOTAL_RESULTS = "number_of_total_results";

    private static final int ROTATE_TIME_MILLIS = 3 * 60 * 60 * 1000; // rotate every 3 hours

    Random random;

    SharedPreferences prefs;
    int max_size;

    public ComicVineArtSource() {
        super(SOURCE_NAME);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUserCommands(BUILTIN_COMMAND_ID_NEXT_ARTWORK);

        random = new Random();
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        max_size = prefs.getInt(NUM_OF_TOTAL_RESULTS, Config.NUM_TOTAL_RESULTS);
    }

    @Override
    protected void onTryUpdate(int reason) throws RetryException {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Config.API_ENDPOINT)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addQueryParam("api_key", Config.CONSUMER_KEY);
                    }
                })
                .setErrorHandler(new ErrorHandler() {
                    @Override
                    public Throwable handleError(RetrofitError retrofitError) {
                        int statusCode = retrofitError.getResponse().getStatus();
                        if (retrofitError.getKind() == RetrofitError.Kind.NETWORK
                                || (500 <= statusCode && statusCode < 600)) {
                            return new RetryException();
                        }
                        scheduleUpdate(System.currentTimeMillis() + ROTATE_TIME_MILLIS);
                        return retrofitError;
                    }
                })
                .build();



        final ComicVineService service = restAdapter.create(ComicVineService.class);
        Comic comic = getNextComic(service);
        if (comic != null) {
            StringBuilder name = new StringBuilder();
            if (comic.getVolume().getName() != null) {
                name.append(comic.getVolume().getName());
            }
            if (comic.getName() != null) {
                name.append(" | ")
                        .append(comic.getName());
            }

            publishArtwork(new Artwork.Builder()
                    .title(name.toString())
                    .byline("Data provided by © ComicVine.")
                    .imageUri(Uri.parse(comic.getImage().getSuperUrl()))
                    .token(String.valueOf(comic.getId()))
                    .viewIntent(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(comic.getSiteDetailUrl())))
                    .build());

            scheduleUpdate(System.currentTimeMillis() + ROTATE_TIME_MILLIS);
        }
    }

    private Comic getNextComic(final ComicVineService service) throws RetryException {
        String currentToken = (getCurrentArtwork() != null) ? getCurrentArtwork().getToken() : null;

        ComicVineService.ComicVineResponse response =
                service.getIssues(String.valueOf(random.nextInt(max_size)));

        if (response == null || response.getResults() == null) {
            throw new RetryException();
        }

        max_size = response.getNumberOfTotalResult();
        prefs.edit().putInt(NUM_OF_TOTAL_RESULTS, max_size).apply();

        if (response.getResults().size() < 1) {
            scheduleUpdate(System.currentTimeMillis() + ROTATE_TIME_MILLIS);
            return null;
        }
        Comic comic = response.getResults().get(0);
        String newToken = String.valueOf(comic.getId());
        if (currentToken == null || !currentToken.equals(newToken)) {
            return comic;
        } else {
            return getNextComic(service);
        }
    }
}

