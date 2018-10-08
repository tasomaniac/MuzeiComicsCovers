package com.tasomaniac.muzei.comiccovers;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.google.android.apps.muzei.api.provider.Artwork;
import com.google.android.apps.muzei.api.provider.ProviderContract;
import com.tasomaniac.muzei.comiccovers.model.Comic;
import com.tasomaniac.muzei.comiccovers.model.ComicVineResponse;
import com.tasomaniac.muzei.comiccovers.util.IOUtil;
import timber.log.Timber;

import java.io.IOException;
import java.util.Random;

public class ComicVineWorker extends Worker {

    private static final String NUM_OF_TOTAL_RESULTS = "number_of_total_results";
    private static final String COMIC_ID = "comic_id";

    private final Random random = new Random();
    private final SharedPreferences prefs;

    private int max_size;

    public ComicVineWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        max_size = prefs.getInt(NUM_OF_TOTAL_RESULTS, Config.NUM_TOTAL_RESULTS);
    }

    @NonNull @Override public Result doWork() {
        ComicVineService service = ComicVineServiceFactory.createComicVineService(getApplicationContext());

        try {
            Comic comic = getNextComic(service);

            if (comic == null) {
                return Result.FAILURE;
            }

            prefs.edit().putString(COMIC_ID, String.valueOf(comic.getId())).apply();

            ProviderContract.Artwork.addArtwork(
                    getApplicationContext(),
                    BuildConfig.COMIC_VINE_AUTHORITY,
                    toArtwork(comic));
            return Result.SUCCESS;
        } catch (IOException e) {
            Timber.e(e, "No internet connection");
            return Result.RETRY;
        }
    }

    private Comic getNextComic(final ComicVineService service) throws IOException {
        String lastComicId = prefs.getString(COMIC_ID, null);

        String offset = String.valueOf(random.nextInt(max_size));
        ComicVineResponse response = service.getIssues(offset).execute().body();

        if (response == null || response.getResults() == null || response.getResults().isEmpty()) {
            Timber.d("Error getting the image %s", offset);
            return null;
        }

        max_size = response.getNumberOfTotalResult();
        prefs.edit().putInt(NUM_OF_TOTAL_RESULTS, max_size).apply();

        Comic comic = response.getResults().get(0);
        String newComicId = String.valueOf(comic.getId());
        boolean isContentValid = IOUtil.checkContentType(
                getApplicationContext(),
                comic.getImage().getSuperUrl()
        );
        if (!newComicId.equals(lastComicId) && isContentValid) {
            return comic;
        } else {
            return getNextComic(service);
        }
    }

    private Artwork toArtwork(Comic comic) {
        StringBuilder name = new StringBuilder();
        if (comic.getVolume().getName() != null) {
            name.append(comic.getVolume().getName());
        }
        if (comic.getName() != null) {
            if (name.length() > 0) {
                name.append(" | ");
            }
            name.append(comic.getName());
        }

        StringBuilder byline = new StringBuilder();
        if (comic.getIssueNumber() != 0) {
            byline.append("Issue #").append(comic.getIssueNumber());
        }
        if (comic.getCoverDate() != null) {
            if (byline.length() > 0) {
                byline.append(" | ");
            }
            byline.append(comic.getCoverDate());
        }

        return new Artwork.Builder()
                .title(name.toString())
                .byline(byline.toString())
                .attribution("Data provided by © ComicVine.")
                .persistentUri(Uri.parse(comic.getImage().getSuperUrl()))
                .token(String.valueOf(comic.getId()))
                .webUri(Uri.parse(comic.getSiteDetailUrl()))
                .build();
    }

    static void enqueue() {
        WorkManager.getInstance().enqueue(
                new OneTimeWorkRequest.Builder(ComicVineWorker.class)
                        .setConstraints(new Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                        )
                        .build()
        );
    }
}
