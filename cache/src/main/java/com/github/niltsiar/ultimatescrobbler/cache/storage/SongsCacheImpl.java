package com.github.niltsiar.ultimatescrobbler.cache.storage;

import android.content.Context;
import android.database.Cursor;
import com.github.niltsiar.ultimatescrobbler.cache.database.PlayedSongColumns;
import com.github.niltsiar.ultimatescrobbler.cache.database.SongsProvider;
import com.github.niltsiar.ultimatescrobbler.cache.mapper.CacheSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.repository.SongsCache;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
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
            context.getContentResolver()
                   .insert(SongsProvider.PlayedSongs.PLAYED_SONGS, CacheSongMapper.mapToCache(playedSongEntity));
        });
    }

    @Override
    public Single<Long> countStoredPlayedSongs() {
        return Single.fromCallable(() -> {
            long count = 0;
            String[] projection = new String[]{QUERY_COUNT};
            try (Cursor cursor = context.getContentResolver()
                                        .query(SongsProvider.PlayedSongs.PLAYED_SONGS, projection, null, null, null)) {
                if (null == cursor || 0 == cursor.getCount()) {
                    return count;
                }
                cursor.moveToFirst();
                count = cursor.getLong(0);
            }
            return count;
        });
    }

    @Override
    public Single<List<PlayedSongEntity>> getStoredPlayedSongs() {
        return Single.fromCallable(() -> {
            List<PlayedSongEntity> list = new ArrayList<>();
            try (Cursor cursor = context.getContentResolver()
                                        .query(SongsProvider.PlayedSongs.PLAYED_SONGS, PlayedSongColumns.Query.PROJECTION, null, null, null)) {
                int count = 0;
                if (null == cursor) {
                    return list;
                } else {
                    count = cursor.getCount();
                    if (0 == count) {
                        return list;
                    }
                }

                for (int index = 0; index < count; index++) {
                    cursor.moveToPosition(index);
                    list.add(CacheSongMapper.mapFromCache(cursor));
                }
            }
            return list;
        });
    }
}
