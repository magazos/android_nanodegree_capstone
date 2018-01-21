package com.github.niltsiar.ultimatescrobbler.ui.songs.playedsongs;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.github.niltsiar.ultimatescrobbler.utils.LoaderProvider;
import javax.inject.Inject;

public class PlayedSongsViewModelFactory implements ViewModelProvider.Factory {

    private LoaderProvider loaderProvider;

    @Inject
    public PlayedSongsViewModelFactory(LoaderProvider loaderProvider) {
        this.loaderProvider = loaderProvider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public PlayedSongsViewModel create(@NonNull Class modelClass) {
        return new PlayedSongsViewModel(loaderProvider);
    }
}
