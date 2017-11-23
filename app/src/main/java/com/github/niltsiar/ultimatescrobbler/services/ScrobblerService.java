package com.github.niltsiar.ultimatescrobbler.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.github.niltsiar.ultimatescrobbler.receivers.SpotifyReceiver;
import dagger.android.AndroidInjection;
import javax.inject.Inject;

public class ScrobblerService extends Service {

    @Inject
    SpotifyReceiver spotifyReceiver;

    private static final String START_ACTION = "START_SCROBBLER_SERVICE";
    private static final String STOP_ACTION = "STOP_SCROBBLER_SERVICE";

    public static Intent createStartIntent(Context context) {
        Intent startIntent = new Intent(context, ScrobblerService.class);
        startIntent.setAction(START_ACTION);
        return startIntent;
    }

    public static Intent createStopIntent(Context context) {
        Intent stopIntent = new Intent(context, ScrobblerService.class);
        stopIntent.setAction(STOP_ACTION);
        return stopIntent;
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent.getAction()) {
            throw new IllegalArgumentException("intent.getAction() cannot be null");
        }
        switch (intent.getAction()) {
            case START_ACTION:
                break;
            case STOP_ACTION:
                break;
        }
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
