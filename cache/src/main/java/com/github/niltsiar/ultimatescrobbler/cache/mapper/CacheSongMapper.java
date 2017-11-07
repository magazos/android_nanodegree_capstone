package com.github.niltsiar.ultimatescrobbler.cache.mapper;

import android.database.Cursor;
import com.github.niltsiar.ultimatescrobbler.cache.database.PlayedSongColumns;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;

public class CacheSongMapper {

    private CacheSongMapper() {
        //Avoid instances
    }

    public static PlayedSongEntity mapFromCache(Cursor cursor) {
        return PlayedSongEntity.builder()
                               .setId(cursor.getString(PlayedSongColumns.Query.Index.ID))
                               .setTrackName(cursor.getString(PlayedSongColumns.Query.Index.TRACK_NAME))
                               .setArtistName(cursor.getString(PlayedSongColumns.Query.Index.ARTIST_NAME))
                               .setAlbumName(cursor.getString(PlayedSongColumns.Query.Index.ALBUM_NAME))
                               .setDuration(cursor.getInt(PlayedSongColumns.Query.Index.LENGTH))
                               .build();
    }
}
