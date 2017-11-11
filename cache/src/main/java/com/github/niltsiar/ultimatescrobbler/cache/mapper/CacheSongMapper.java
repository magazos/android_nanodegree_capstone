package com.github.niltsiar.ultimatescrobbler.cache.mapper;

import android.content.ContentValues;
import android.database.Cursor;
import com.github.niltsiar.ultimatescrobbler.cache.database.PlayedSongColumns;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import org.threeten.bp.Instant;

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
                               .setTimestamp(Instant.ofEpochMilli(cursor.getLong(PlayedSongColumns.Query.Index.TIMESTAMP)))
                               .build();
    }

    public static ContentValues mapToCache(PlayedSongEntity playedSongEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PlayedSongColumns.ID, playedSongEntity.getId());
        contentValues.put(PlayedSongColumns.TRACK_NAME, playedSongEntity.getTrackName());
        contentValues.put(PlayedSongColumns.ARTIST_NAME, playedSongEntity.getArtistName());
        contentValues.put(PlayedSongColumns.ALBUM_NAME, playedSongEntity.getAlbumName());
        contentValues.put(PlayedSongColumns.LENGTH, playedSongEntity.getDuration());
        contentValues.put(PlayedSongColumns.TIMESTAMP, playedSongEntity.getTimestamp()
                                                                       .toEpochMilli());

        return contentValues;
    }
}
