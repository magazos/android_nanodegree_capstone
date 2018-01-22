package com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs;

import android.arch.lifecycle.ViewModel;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.github.niltsiar.ultimatescrobbler.utils.LoaderProvider;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;

public class ScrobbledSongsViewModel extends ViewModel implements LoaderManager.LoaderCallbacks<Cursor> {

    private LoaderProvider loaderProvider;
    private BehaviorRelay<ScrobbledSongsAdapter> adapterRelay;

    public ScrobbledSongsViewModel(LoaderProvider loaderProvider) {
        this.loaderProvider = loaderProvider;
        adapterRelay = BehaviorRelay.create();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return loaderProvider.createScrobbledSongsLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ScrobbledSongsAdapter adapter = new ScrobbledSongsAdapter(data);
        adapterRelay.accept(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public Observable<ScrobbledSongsAdapter> getAdapter() {
        return adapterRelay;
    }
}
