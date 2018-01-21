package com.github.niltsiar.ultimatescrobbler.di.module;

import com.github.niltsiar.ultimatescrobbler.ui.configuration.ConfigurationActivity;
import com.github.niltsiar.ultimatescrobbler.ui.songdetails.SongDetailsActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract ConfigurationActivity contributeConfigurationActivityInjector();

    @ContributesAndroidInjector
    abstract SongDetailsActivity contributeSongDetailsActivityInjector();
}
