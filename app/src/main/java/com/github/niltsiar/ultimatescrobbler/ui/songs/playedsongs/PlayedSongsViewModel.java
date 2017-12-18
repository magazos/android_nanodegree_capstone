package com.github.niltsiar.ultimatescrobbler.ui.songs.playedsongs;

import android.arch.lifecycle.ViewModel;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.github.niltsiar.ultimatescrobbler.utils.LoaderProvider;
import javax.inject.Inject;

public class PlayedSongsViewModel extends ViewModel implements LoaderManager.LoaderCallbacks<Cursor> {

    private LoaderProvider loaderProvider;

    @Inject
    public PlayedSongsViewModel(LoaderProvider loaderProvider) {
        this.loaderProvider = loaderProvider;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return loaderProvider.createPlayedSongsLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
