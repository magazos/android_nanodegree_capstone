package com.github.niltsiar.ultimatescrobbler.cache.database;

import android.net.Uri;
import com.github.niltsiar.ultimatescrobbler.cache.BuildConfig;
import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = SongsProvider.AUTHORITY,
                 database = SongsDatabase.class,
                 packageName = "com.github.niltsiar.ultimatescrobbler.cache.provider")
public final class SongsProvider {

    private SongsProvider() {
        //Avoid instances
    }

    static final String AUTHORITY = BuildConfig.SONGS_AUTHORITY;

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String PLAYED_SONGS = "played_songs";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder uriBuilder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            uriBuilder.appendPath(path);
        }
        return uriBuilder.build();
    }

    @TableEndpoint(table = SongsDatabase.PLAYED_SONGS)
    public static class PlayedSongs {

        @ContentUri(path = Path.PLAYED_SONGS,
                    type = "vnd.android.cursor.dir/played_song",
                    defaultSort = PlayedSongColumns.PLAYED_INSTANT)
        public static Uri PLAYED_SONGS = buildUri(Path.PLAYED_SONGS);

        @InexactContentUri(path = Path.PLAYED_SONGS + "/#",
                           name = "SONG_ID",
                           type = "vnd.android.cursor.item/played_song",
                           whereColumn = PlayedSongColumns.ID,
                           pathSegment = 1)
        public static Uri withId(String id) {
            return buildUri(Path.PLAYED_SONGS, id);
        }
    }
}
