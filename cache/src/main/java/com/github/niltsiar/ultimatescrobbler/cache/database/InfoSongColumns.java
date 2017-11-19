package com.github.niltsiar.ultimatescrobbler.cache.database;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

public interface InfoSongColumns {

    @DataType(INTEGER)
    @PrimaryKey
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

    @DataType(TEXT)
    @NotNull
    String ALBUM_ARTIST_NAME = "album_artist_name";

    @DataType(TEXT)
    @NotNull
    String TAGS = "tags";

    @DataType(TEXT)
    @NotNull
    String WIKI_CONTENT = "wiki_content";

    interface Query {
        String[] PROJECTION = {ID, TRACK_NAME, ARTIST_NAME, ALBUM_NAME, ALBUM_ARTIST_NAME, TAGS, WIKI_CONTENT
        };

        interface Index {
            int ID = 0;
            int TRACK_NAME = 1;
            int ARTIST_NAME = 2;
            int ALBUM_NAME = 3;
            int ALBUM_ARTIST_NAME = 4;
            int TAGS = 5;
            int WIKI_CONTENT = 6;
        }
    }
}
