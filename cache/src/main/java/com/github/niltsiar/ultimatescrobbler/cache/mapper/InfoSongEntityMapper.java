package com.github.niltsiar.ultimatescrobbler.cache.mapper;

import android.content.ContentValues;
import android.database.Cursor;
import com.github.niltsiar.ultimatescrobbler.cache.database.InfoSongColumns;
import com.github.niltsiar.ultimatescrobbler.data.model.InfoSongEntity;
import java.util.Arrays;
import org.threeten.bp.Instant;

public class InfoSongEntityMapper {

    private static final String TAG_SEPARATOR = "#";

    private InfoSongEntityMapper() {
        //Avoid instances
    }

    public static InfoSongEntity mapFromCache(Cursor cursor) {
        String[] tagArray = cursor.getString(InfoSongColumns.Query.Index.TAGS)
                                  .split(TAG_SEPARATOR);

        return InfoSongEntity.builder()
                             .setTrackName(cursor.getString(InfoSongColumns.Query.Index.TRACK_NAME))
                             .setArtist(cursor.getString(InfoSongColumns.Query.Index.ARTIST_NAME))
                             .setAlbum(cursor.getString(InfoSongColumns.Query.Index.ALBUM_NAME))
                             .setAlbumArtist(cursor.getString(InfoSongColumns.Query.Index.ALBUM_ARTIST_NAME))
                             .setAlbumArtUrl(cursor.getString(InfoSongColumns.Query.Index.ALBUM_ART_URL))
                             .setWikiContent(cursor.getString(InfoSongColumns.Query.Index.WIKI_CONTENT))
                             .setTimestamp(Instant.ofEpochMilli(cursor.getLong(InfoSongColumns.Query.Index.ID)))
                             .setTags(Arrays.asList(tagArray))
                             .build();
    }

    public static ContentValues mapToCache(InfoSongEntity infoSongEntity) {
        StringBuilder tags = new StringBuilder();
        for (String tag : infoSongEntity.getTags()) {
            tags.append(tag);
            tags.append(TAG_SEPARATOR);
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(InfoSongColumns.ID, infoSongEntity.getTimestamp()
                                                            .toEpochMilli());
        contentValues.put(InfoSongColumns.TRACK_NAME, infoSongEntity.getTrackName());
        contentValues.put(InfoSongColumns.ARTIST_NAME, infoSongEntity.getArtist());
        contentValues.put(InfoSongColumns.ALBUM_NAME, infoSongEntity.getAlbum());
        contentValues.put(InfoSongColumns.ALBUM_ARTIST_NAME, infoSongEntity.getAlbumArtist());
        contentValues.put(InfoSongColumns.ALBUM_ART_URL, infoSongEntity.getAlbumArtUrl());
        contentValues.put(InfoSongColumns.WIKI_CONTENT, infoSongEntity.getWikiContent());
        contentValues.put(InfoSongColumns.TAGS, tags.toString());

        return contentValues;
    }
}
