package com.github.niltsiar.ultimatescrobbler.data.mapper;

import com.github.niltsiar.ultimatescrobbler.data.model.ScrobbledSongEntity;
import com.github.niltsiar.ultimatescrobbler.domain.model.ScrobbledSong;

public class ScrobbledSongMapper implements Mapper<ScrobbledSongEntity, ScrobbledSong> {

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
