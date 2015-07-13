package com.tasomaniac.muzei.comiccovers.util;

import android.net.Uri;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import timber.log.Timber;

public class IOUtil {
    private static final int DEFAULT_READ_TIMEOUT = 30 * 1000; // 30s
    private static final int DEFAULT_CONNECT_TIMEOUT = 15 * 1000; // 15s

    public static boolean checkContentType(Uri uri, String reqContentTypeSubstring)
            throws OpenUriException {

        if (uri == null) {
            throw new IllegalArgumentException("Uri cannot be empty");
        }

        String scheme = uri.getScheme();
        if (scheme == null) {
            throw new OpenUriException(false, new IOException("Uri had no scheme"));
        }

        OkHttpClient client = new OkHttpClient();
        HttpURLConnection conn;
        int responseCode = 0;
        String responseMessage = null;
        try {
            conn = new OkUrlFactory(client).open(new URL(uri.toString()));
        } catch (MalformedURLException e) {
            throw new OpenUriException(false, e);
        }

        try {
            conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
            conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
            responseCode = conn.getResponseCode();
            responseMessage = conn.getResponseMessage();
            if (!(responseCode >= 200 && responseCode < 300)) {
                throw new IOException("HTTP error response.");
            }
            if (reqContentTypeSubstring != null) {
                String contentType = conn.getContentType();
                if (contentType == null || !contentType.contains(reqContentTypeSubstring)) {

                    Timber.e("Wrong content type. Will retry in an hour. ");
                    return false;
                }
            }

        } catch (IOException e) {
            if (responseCode > 0) {
                throw new OpenUriException(
                        500 <= responseCode && responseCode < 600,
                        responseMessage, e);
            } else {
                throw new OpenUriException(false, e);
            }
        }

        return true;
    }

    public static class OpenUriException extends Exception {
        private boolean mRetryable;

        public OpenUriException(boolean retryable, String message, Throwable cause) {
            super(message, cause);
            mRetryable = retryable;
        }

        public OpenUriException(boolean retryable, Throwable cause) {
            super(cause);
            mRetryable = retryable;
        }

        public boolean isRetryable() {
            return mRetryable;
        }
    }
}