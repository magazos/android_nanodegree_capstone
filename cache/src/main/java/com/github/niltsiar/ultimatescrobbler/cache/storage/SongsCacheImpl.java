package com.github.niltsiar.ultimatescrobbler.cache.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.github.niltsiar.ultimatescrobbler.cache.database.PlayedSongColumns;
import com.github.niltsiar.ultimatescrobbler.cache.database.SongsProvider;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.repository.SongsCache;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.Calendar;
import javax.inject.Inject;

public class SongsCacheImpl implements SongsCache {

    //This will store the application context so it will not create leaks
    private Context context;

    private static final String QUERY_COUNT = "count(*) as count";

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

    @Override
    public Single<Long> countStoredPlayedSongs() {
        return Single.fromCallable(() -> {
            long count = 0;
            String[] projection = new String[]{QUERY_COUNT};
            try (Cursor cursor = context.getContentResolver()
                                        .query(SongsProvider.PlayedSongs.PLAYED_SONGS, projection, null, null, null)) {
                if (null != cursor && 0 != cursor.getCount()) {
                    cursor.moveToFirst();
                    count = cursor.getLong(0);
                }
            }
            return count;
        });
    }
}
