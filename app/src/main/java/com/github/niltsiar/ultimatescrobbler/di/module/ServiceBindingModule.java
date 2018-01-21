package com.github.niltsiar.ultimatescrobbler.di.module;

import com.github.niltsiar.ultimatescrobbler.services.ScrobblePlayedSongsService;
import com.github.niltsiar.ultimatescrobbler.services.ScrobblerService;
import com.github.niltsiar.ultimatescrobbler.services.SendNowPlayingService;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceBindingModule {

    @ContributesAndroidInjector
    abstract ScrobblerService contributeScrobblerService();

    @ContributesAndroidInjector
    abstract SendNowPlayingService contributeSendNowPlayingServiceInjector();

    @ContributesAndroidInjector
    abstract ScrobblePlayedSongsService contributeScrobblePlayedSongsService();
}
