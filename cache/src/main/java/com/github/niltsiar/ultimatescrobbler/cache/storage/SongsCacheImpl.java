package com.github.niltsiar.ultimatescrobbler.cache.storage;

import android.content.ContentValues;
import android.content.Context;
import com.github.niltsiar.ultimatescrobbler.cache.database.PlayedSongColumns;
import com.github.niltsiar.ultimatescrobbler.cache.database.SongsProvider;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.repository.SongsCache;
import io.reactivex.Completable;
import java.util.Calendar;
import javax.inject.Inject;

public class SongsCacheImpl implements SongsCache {

    //This will store the application context so it will not create leaks
    private Context context;

    @Inject
    public SongsCacheImpl(Context context) {
        this.context = context;
    }

    @Override
    public Completable savePlayedSong(PlayedSongEntity playedSongEntity) {
        return Completable.fromAction(() -> {
            ContentValues cv = new ContentValues();
            cv.put(PlayedSongColumns.ID, playedSongEntity.getId());
            cv.put(PlayedSongColumns.TRACK_NAME, playedSongEntity.getTrackName());
            cv.put(PlayedSongColumns.ARTIST_NAME, playedSongEntity.getArtistName());
            cv.put(PlayedSongColumns.ALBUM_NAME, playedSongEntity.getAlbumName());
            cv.put(PlayedSongColumns.LENGTH, playedSongEntity.getDuration());
            cv.put(PlayedSongColumns.PLAYED_INSTANT, Calendar.getInstance()
                                                             .getTimeInMillis());
            context.getContentResolver()
                   .insert(SongsProvider.PlayedSongs.PLAYED_SONGS, cv);
        });
    }
}
