package com.github.niltsiar.ultimatescrobbler.ui.songdetails;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.github.niltsiar.ultimatescrobbler.utils.LoaderProvider;
import javax.inject.Inject;

public class SongDetailsViewModelFactory implements ViewModelProvider.Factory {

    private LoaderProvider loaderProvider;

    @Inject
    public SongDetailsViewModelFactory(LoaderProvider loaderProvider) {
        this.loaderProvider = loaderProvider;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public SongDetailsViewModel create(@NonNull Class modelClass) {
        return new SongDetailsViewModel(loaderProvider);
    }
}
