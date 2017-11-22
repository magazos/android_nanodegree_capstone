package com.github.niltsiar.ultimatescrobbler.remote.mapper;

import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.ScrobbledSongEntity;
import com.github.niltsiar.ultimatescrobbler.remote.model.ScrobbledSongModel;
import javax.inject.Inject;
import org.threeten.bp.Instant;

public class ScrobbledSongMapper implements EntityMapper<ScrobbledSongModel, ScrobbledSongEntity> {

    @Inject
    public ScrobbledSongMapper() {
    }

    @Override
    public ScrobbledSongEntity mapFromRemote(ScrobbledSongModel scrobbledSongModel) {
        return ScrobbledSongEntity.builder()
                                  .setTrackName(scrobbledSongModel.getTrackName())
                                  .setArtist(scrobbledSongModel.getArtist())
                                  .setAlbum(scrobbledSongModel.getAlbum())
                                  .setAlbumArtist(scrobbledSongModel.getAlbumArtist())
                                  .setTimestamp(Instant.ofEpochSecond(scrobbledSongModel.getTimeStamp()))
                                  .build();
    }

    public ScrobbledSongEntity mapFromRemote(ScrobbledSongModel scrobbledSongModel, PlayedSongEntity playedSongEntity) {
        return mapFromRemote(scrobbledSongModel).withId(playedSongEntity.getId());
    }
}
