package com.github.niltsiar.ultimatescrobbler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import com.github.niltsiar.ultimatescrobbler.di.DaggerApplicationComponent;
import com.github.niltsiar.ultimatescrobbler.services.ScrobblerService;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import javax.inject.Inject;

public class UltimateScrobblerApplication extends CustomApplication implements HasActivityInjector, HasServiceInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Service> serviceDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.builder()
                                  .application(this)
                                  .build()
                                  .inject(this);

        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            createNotificationChannels();
        }
        ContextCompat.startForegroundService(this, ScrobblerService.createStartIntent(this));
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return serviceDispatchingAndroidInjector;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannels() {
        String channelName = getResources().getString(R.string.app_name);
        int importance = NotificationManager.IMPORTANCE_NONE;
        NotificationChannel notificationChannel = new NotificationChannel(ScrobblerService.NOTIFICATION_CHANNEL, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
    }
}
