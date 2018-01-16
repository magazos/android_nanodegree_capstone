package com.github.niltsiar.ultimatescrobbler.di.module;

import com.github.niltsiar.ultimatescrobbler.ui.configuration.ConfigurationActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract ConfigurationActivity contributeConfigurationActivityInjector();
}
