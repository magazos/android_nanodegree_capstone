package com.github.niltsiar.ultimatescrobbler;

import android.app.Application;
import timber.log.Timber;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
