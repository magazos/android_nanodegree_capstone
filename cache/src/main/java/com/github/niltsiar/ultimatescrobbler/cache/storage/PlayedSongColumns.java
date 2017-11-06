package com.github.niltsiar.ultimatescrobbler.cache.storage;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

public interface PlayedSongColumns {

    @DataType(TEXT)
    @PrimaryKey
    String ID = "_id";

    @DataType(TEXT)
    @NotNull
    String TRACK_NAME = "track_name";

    @DataType(TEXT)
    @NotNull
    String ARTIST_NAME = "artist_name";

    @DataType(TEXT)
    String ALBUM_NAME = "album_name";

    @DataType(INTEGER)
    @NotNull
    String LENGTH = "length";

    @DataType(INTEGER)
    @NotNull
    String PLAYED_INSTANT = "played_instant";

}
