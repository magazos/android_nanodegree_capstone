package com.github.niltsiar.ultimatescrobbler.cache.database;

import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

public interface PlayedSongColumns {

    @DataType(TEXT)
    @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
    String ID = "_id";

    @DataType(TEXT)
    @NotNull
    String TRACK_NAME = "track_name";

    @DataType(TEXT)
    @NotNull
    String ARTIST_NAME = "artist_name";

    @DataType(TEXT)
    @NotNull
    String ALBUM_NAME = "album_name";

    @DataType(INTEGER)
    @NotNull
    String LENGTH = "length";

    @DataType(INTEGER)
    @NotNull
    String TIMESTAMP = "played_instant";

    @DataType(INTEGER)
    @NotNull
    String SCROBBLED = "scrobbled";

    interface Query {
        String[] PROJECTION = {ID, TRACK_NAME, ARTIST_NAME, ALBUM_NAME, LENGTH, TIMESTAMP,
        };

        interface Index {
            int ID = 0;
            int TRACK_NAME = 1;
            int ARTIST_NAME = 2;
            int ALBUM_NAME = 3;
            int LENGTH = 4;
            int TIMESTAMP = 5;
        }
    }
}
