package com.tasomaniac.muzei.comiccovers.util;

import android.content.Context;
import com.tasomaniac.muzei.comiccovers.App;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

import java.io.IOException;

public class IOUtil {

    private static final String CONTENT_TYPE = "image/";

    public static boolean checkContentType(Context context, String uri)
            throws IOException {

        if (uri == null) {
            throw new IllegalArgumentException("Uri cannot be empty");
        }

        Request request = new Request.Builder()
                .url(uri)
                .build();

        Response response = App.get(context).getOkHttpClient().newCall(request).execute();

        int responseCode = response.code();
        if (!(responseCode >= 200 && responseCode < 300)) {
            throw new IOException("HTTP error response.");
        }
        String contentType = response.header("Content-Type");
        if (contentType == null || !contentType.contains(CONTENT_TYPE)) {
            Timber.e("Wrong content type. Will retry in an hour. ");
            return false;
        }

        return true;
    }
}
