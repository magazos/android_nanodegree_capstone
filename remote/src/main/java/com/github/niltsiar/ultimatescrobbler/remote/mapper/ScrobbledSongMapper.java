package com.github.niltsiar.ultimatescrobbler.remote.mapper;

import com.github.niltsiar.ultimatescrobbler.data.model.ScrobbledSongEntity;
import com.github.niltsiar.ultimatescrobbler.remote.model.ScrobbledSongModel;
import javax.inject.Inject;
import org.threeten.bp.Instant;

public class ScrobbledSongMapper implements EntityMapper<ScrobbledSongModel, ScrobbledSongEntity> {

    @Inject
    public ScrobbledSongMapper() {
    }

    @Override
    public ScrobbledSongEntity mapFromRemote(ScrobbledSongModel type) {
        return ScrobbledSongEntity.builder()
                                  .setTrackName(type.getTrackName())
                                  .setArtist(type.getArtist())
                                  .setAlbum(type.getAlbum())
                                  .setAlbumArtist(type.getAlbumArtist())
                                  .setTimestamp(Instant.ofEpochSecond(type.getTimeStamp()))
                                  .build();
    }
}
