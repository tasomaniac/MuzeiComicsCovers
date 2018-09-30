package com.tasomaniac.muzei.comiccovers;

import android.content.Context;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ComicVineServiceFactory {

    private ComicVineServiceFactory() {
        //no instance
    }

    static ComicVineService createComicVineService(Context context) {
        OkHttpClient client = App.get(context).getOkHttpClient()
                .newBuilder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url()
                            .newBuilder()
                            .addQueryParameter("api_key", BuildConfig.COMIC_VINE_CONSUMER_KEY)
                            .build();
                    return chain.proceed(request.newBuilder().url(url).build());
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Config.API_ENDPOINT)
                .build();

        return retrofit.create(ComicVineService.class);
    }
}
