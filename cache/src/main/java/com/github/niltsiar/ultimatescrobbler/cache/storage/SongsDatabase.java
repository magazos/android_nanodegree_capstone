package com.github.niltsiar.ultimatescrobbler.cache.storage;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = SongsDatabase.VERSION,
          packageName = "com.github.niltsiar.ultimatescrobbler.cache.provider")
public class SongsDatabase {

    private SongsDatabase() {
        //Avoid instances
    }

    public static final int VERSION = 1;

    @Table(PlayedSongColumns.class)
    public static final String PLAYED_SONGS = "played_songs";
}
