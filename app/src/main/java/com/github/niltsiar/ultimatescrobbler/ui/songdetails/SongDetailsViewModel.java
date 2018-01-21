package com.github.niltsiar.ultimatescrobbler.ui.songdetails;

import android.arch.lifecycle.ViewModel;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.github.niltsiar.ultimatescrobbler.cache.mapper.InfoSongEntityMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.InfoSongMapper;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;
import com.github.niltsiar.ultimatescrobbler.utils.LoaderProvider;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;

public class SongDetailsViewModel extends ViewModel implements LoaderManager.LoaderCallbacks<Cursor> {

    private LoaderProvider loaderProvider;
    private BehaviorRelay<InfoSong> infoSongRelay;

    private static String SONG_ID_KEY = "SONG_ID_KEY";

    public static Bundle createLoaderBundle(String songId) {
        Bundle bundle = new Bundle();
        bundle.putString(SONG_ID_KEY, songId);
        return bundle;
    }

    public SongDetailsViewModel(LoaderProvider loaderProvider) {
        this.loaderProvider = loaderProvider;
        infoSongRelay = BehaviorRelay.create();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null == args || !args.containsKey(SONG_ID_KEY)) {
            throw new IllegalArgumentException();
        }
        String songId = args.getString(SONG_ID_KEY);
        return loaderProvider.createInfoSongLoader(songId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        InfoSongMapper mapper = new InfoSongMapper();
        InfoSong infoSong = mapper.mapFromEntity(InfoSongEntityMapper.mapFromCache(data));
        infoSongRelay.accept(infoSong);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public Observable<InfoSong> getInfoSong() {
        return infoSongRelay;
    }
}
