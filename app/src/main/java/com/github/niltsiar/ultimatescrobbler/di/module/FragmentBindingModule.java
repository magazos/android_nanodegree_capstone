package com.github.niltsiar.ultimatescrobbler.di.module;

import com.github.niltsiar.ultimatescrobbler.ui.songs.playedsongs.PlayedSongsFragment;
import com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs.ScrobbledSongsFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract PlayedSongsFragment contributePlayedSongsFragment();

    @ContributesAndroidInjector
    abstract ScrobbledSongsFragment contributeScrobbledSongsFragment();
}
