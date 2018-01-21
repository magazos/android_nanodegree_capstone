package com.github.niltsiar.ultimatescrobbler.data.mapper;

import com.github.niltsiar.ultimatescrobbler.data.model.InfoSongEntity;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;
import java.util.ArrayList;
import javax.inject.Inject;

public class InfoSongMapper implements Mapper<InfoSongEntity, InfoSong> {

    @Inject
    public InfoSongMapper() {
    }

    @Override
    public InfoSong mapFromEntity(InfoSongEntity type) {
        return InfoSong.builder()
                       .setTrackName(type.getTrackName())
                       .setArtist(type.getArtist())
                       .setAlbum(type.getAlbum())
                       .setAlbumArtist(type.getAlbumArtist())
                       .setAlbumArtUrl(type.getAlbumArtUrl())
                       .setTags(new ArrayList<>(type.getTags()))
                       .setTimestamp(type.getTimestamp())
                       .setWikiContent(type.getWikiContent())
                       .build();
    }

    @Override
    public InfoSongEntity mapToEntity(InfoSong type) {
        return InfoSongEntity.builder()
                             .setTrackName(type.getTrackName())
                             .setArtist(type.getArtist())
                             .setAlbum(type.getAlbum())
                             .setAlbumArtist(type.getAlbumArtist())
                             .setAlbumArtUrl(type.getAlbumArtUrl())
                             .setTags(new ArrayList<>(type.getTags()))
                             .setTimestamp(type.getTimestamp())
                             .setWikiContent(type.getWikiContent())
                             .build();
    }
}
