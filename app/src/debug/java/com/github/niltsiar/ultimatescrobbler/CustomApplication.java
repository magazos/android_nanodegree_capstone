package com.github.niltsiar.ultimatescrobbler;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.timber.StethoTree;
import com.github.niltsiar.ultimatescrobbler.util.LinkingDebugTree;
import com.squareup.leakcanary.LeakCanary;
import timber.log.Timber;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);

        Timber.plant(new LinkingDebugTree());

        Stetho.initializeWithDefaults(this);

        Timber.plant(new StethoTree());
    }
}
