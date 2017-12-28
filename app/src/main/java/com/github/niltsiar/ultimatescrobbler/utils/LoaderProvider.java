package com.github.niltsiar.ultimatescrobbler.utils;

import android.content.Context;
import android.support.v4.content.CursorLoader;
import com.github.niltsiar.ultimatescrobbler.cache.database.PlayedSongColumns;
import com.github.niltsiar.ultimatescrobbler.cache.database.SongsProvider;
import javax.inject.Inject;

public class LoaderProvider {

    private Context context;

    @Inject
    public LoaderProvider(Context context) {
        this.context = context;
    }

    public CursorLoader createPlayedSongsLoader() {
        return new CursorLoader(context, SongsProvider.PlayedSongs.PLAYED_SONGS, PlayedSongColumns.Query.PROJECTION, null, null, null);
    }

    public CursorLoader createScrobbledSongsLoader() {
        return new CursorLoader(context, SongsProvider.InfoSong.INFO_SONG, null, null, null, null);
    }
}
