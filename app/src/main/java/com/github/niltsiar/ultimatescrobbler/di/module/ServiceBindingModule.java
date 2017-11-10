package com.github.niltsiar.ultimatescrobbler.di.module;

import com.github.niltsiar.ultimatescrobbler.remote.services.GetInfoService;
import com.github.niltsiar.ultimatescrobbler.remote.services.ScrobblePlayedSongsService;
import com.github.niltsiar.ultimatescrobbler.remote.services.SendNowPlayingService;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceBindingModule {

    @ContributesAndroidInjector
    abstract SendNowPlayingService contributeSendNowPlayingServiceInjector();

    @ContributesAndroidInjector
    abstract ScrobblePlayedSongsService contributeScrobblePlayedSongsService();

    @ContributesAndroidInjector
    abstract GetInfoService contributeGetInfoService();
}
