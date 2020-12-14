package com.tasomaniac.muzei.comiccovers;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import timber.log.Timber;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    public static final int DEFAULT_READ_TIMEOUT = 30 * 1000; // 30s
    public static final int DEFAULT_CONNECT_TIMEOUT = 15 * 1000; // 15s
    private OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, @NotNull String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
            crashlytics.log(tag + message);
            if (t != null && priority >= Log.WARN) {
                crashlytics.recordException(t);
            }
        }
    }
}
