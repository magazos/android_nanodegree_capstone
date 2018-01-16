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
        String INFO_SONG = "info_song";
        String CURRENT_SONG = "current_song";
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
                    defaultSort = PlayedSongColumns.TIMESTAMP)
        public static Uri PLAYED_SONGS = buildUri(Path.PLAYED_SONGS);

        @InexactContentUri(path = Path.PLAYED_SONGS + "/*",
                           name = "SONG_ID",
                           type = "vnd.android.cursor.item/played_song",
                           whereColumn = PlayedSongColumns.ID,
                           pathSegment = 1)
        public static Uri withId(String id) {
            return buildUri(Path.PLAYED_SONGS, id);
        }
    }

    @TableEndpoint(table = SongsDatabase.INFO_SONG)
    public static class InfoSong {
        @ContentUri(path = Path.INFO_SONG,
                    type = "vnd.android.cursor.dir/info_song",
                    defaultSort = InfoSongColumns.ID)
        public static Uri INFO_SONG = buildUri(Path.INFO_SONG);
    }

    @TableEndpoint(table = SongsDatabase.CURRENT_SONG)
    public static class CurrentSong {
        @ContentUri(path = Path.CURRENT_SONG,
                    type = "vnd.android.cursor.dir/current_song",
                    defaultSort = PlayedSongColumns.ID)
        public static Uri CURRENT_SONG = buildUri(Path.CURRENT_SONG);

        @InexactContentUri(path = Path.CURRENT_SONG + "/*",
                           name = "SONG_ID",
                           type = "vnd.android.cursor.item/current_song",
                           whereColumn = PlayedSongColumns.ID,
                           pathSegment = 1)
        public static Uri withId(String id) {
            return buildUri(Path.CURRENT_SONG, id);
        }
    }
}
