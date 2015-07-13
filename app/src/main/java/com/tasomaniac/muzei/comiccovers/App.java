package com.tasomaniac.muzei.comiccovers;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.squareup.okhttp.OkHttpClient;
import com.tasomaniac.muzei.comiccovers.util.IOUtil;

import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by tasomaniac on 13/7/15.
 */
public class App extends Application {

    private OkHttpClient okHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();

        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(IOUtil.DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(IOUtil.DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Fabric.with(this, new Crashlytics());
            Timber.plant(new CrashReportingTree());
        }
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }


    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            Crashlytics.log(priority, tag, message);
            if (t != null && priority >= Log.WARN) {
                Crashlytics.logException(t);
            }
        }
    }
}
