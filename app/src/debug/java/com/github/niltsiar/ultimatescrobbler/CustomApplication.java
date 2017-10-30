package com.github.niltsiar.ultimatescrobbler;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.timber.StethoTree;
import timber.log.Timber;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        Stetho.initializeWithDefaults(this);

        Timber.plant(new StethoTree());
    }
}
