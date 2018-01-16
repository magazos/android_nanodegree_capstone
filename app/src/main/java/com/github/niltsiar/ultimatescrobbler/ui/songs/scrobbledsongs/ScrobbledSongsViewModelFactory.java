package com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.github.niltsiar.ultimatescrobbler.utils.LoaderProvider;
import javax.inject.Inject;

public class ScrobbledSongsViewModelFactory implements ViewModelProvider.Factory {

    private LoaderProvider loaderProvider;
    private ScrobbledSongsAdapter.OnItemClickListener listener;

    @Inject
    public ScrobbledSongsViewModelFactory(LoaderProvider loaderProvider) {
        this.loaderProvider = loaderProvider;
    }

    public void setSongItemClickListener(ScrobbledSongsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public ScrobbledSongsViewModel create(@NonNull Class modelClass) {
        return new ScrobbledSongsViewModel(loaderProvider, listener);
    }
}
