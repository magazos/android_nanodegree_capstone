package com.github.niltsiar.ultimatescrobbler.data.mapper;

import com.github.niltsiar.ultimatescrobbler.data.model.ScrobbledSongEntity;
import com.github.niltsiar.ultimatescrobbler.domain.model.ScrobbledSong;
import javax.inject.Inject;

public class ScrobbledSongMapper implements Mapper<ScrobbledSongEntity, ScrobbledSong> {

    @Inject
    public ScrobbledSongMapper() {
    }

    @Override
    public ScrobbledSong mapFromEntity(ScrobbledSongEntity type) {
        return ScrobbledSong.builder()
                            .setTrackName(type.getTrackName())
                            .setArtist(type.getArtist())
                            .setAlbum(type.getAlbum())
                            .setAlbumArtist(type.getAlbumArtist())
                            .setTimeStamp(type.getTimeStamp())
                            .build();
    }

    @Override
    public ScrobbledSongEntity mapToEntity(ScrobbledSong type) {
        return ScrobbledSongEntity.builder()
                                  .setTrackName(type.getTrackName())
                                  .setArtist(type.getArtist())
                                  .setAlbum(type.getAlbum())
                                  .setAlbumArtist(type.getAlbumArtist())
                                  .setTimeStamp(type.getTimeStamp())
                                  .build();
    }
}
