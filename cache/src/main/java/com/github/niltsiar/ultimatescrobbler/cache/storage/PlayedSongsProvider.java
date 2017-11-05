package com.github.niltsiar.ultimatescrobbler.cache.storage;

import android.net.Uri;
import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = PlayedSongsProvider.AUTHORITY,
                 database = SongsDatabase.class)
public class PlayedSongsProvider {

    public static final String AUTHORITY = "com.github.niltsiar.ultimatescrobbler.PlayedSongsProvider";

    @TableEndpoint(table = SongsDatabase.PLAYED_SONGS)
    public static class PlayedSongs {

        @ContentUri(path = "songs",
                    type = "vnd.android.cursor.dir/songs",
                    defaultSort = PlayedSongColumns.PLAYED_INSTANT)
        public static final Uri PLAYED_SONGS = Uri.parse("content://" + AUTHORITY + "/songs");
    }
}
