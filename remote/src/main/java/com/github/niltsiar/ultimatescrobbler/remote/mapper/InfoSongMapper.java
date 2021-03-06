package com.github.niltsiar.ultimatescrobbler.remote.mapper;

import android.text.TextUtils;
import com.github.niltsiar.ultimatescrobbler.data.model.InfoSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.ScrobbledSongEntity;
import com.github.niltsiar.ultimatescrobbler.remote.model.InfoSongModel;
import java.util.ArrayList;
import javax.inject.Inject;
import org.threeten.bp.Instant;

public class InfoSongMapper implements EntityMapper<InfoSongModel, InfoSongEntity> {

    @Inject
    public InfoSongMapper() {
    }

    @Override
    public InfoSongEntity mapFromRemote(InfoSongModel type) {
        InfoSongEntity.Builder builder = InfoSongEntity.builder();
        builder.setTrackName(type.getTrackName())
               .setArtist(type.getArtist());

        if (null != type.getAlbum()) {
            builder.setAlbum(type.getAlbum());
        } else {
            builder.setAlbum("");
        }
        if (null != type.getAlbumArtist()) {
            builder.setAlbumArtist(type.getAlbumArtist());
        } else {
            builder.setAlbumArtist("");
        }
        if (null != type.getAlbumArt()) {
            builder.setAlbumArtUrl(type.getAlbumArt());
        } else {
            builder.setAlbumArtUrl("");
        }
        builder.setTags(new ArrayList<>(type.getTags()));
        if (null != type.getContent()) {
            builder.setWikiContent(type.getContent());
        } else {
            builder.setWikiContent("");
        }
        builder.setTimestamp(Instant.now());
        return builder.build();
    }

    public InfoSongEntity mapFromRemote(InfoSongModel infoSong, ScrobbledSongEntity scrobbledSongEntity) {
        InfoSongEntity infoSongEntity = mapFromRemote(infoSong).withTimestamp(scrobbledSongEntity.getTimestamp());
        if (TextUtils.isEmpty(infoSong.getAlbum())) {
            infoSongEntity = infoSongEntity.withAlbum(scrobbledSongEntity.getAlbum());
        }
        return infoSongEntity;
    }

    public InfoSongEntity mapFromEntity(ScrobbledSongEntity scrobbledSongEntity) {
        return InfoSongEntity.builder()
                             .setTrackName(scrobbledSongEntity.getTrackName())
                             .setArtist(scrobbledSongEntity.getArtist())
                             .setAlbum(scrobbledSongEntity.getAlbum())
                             .setAlbumArtist(scrobbledSongEntity.getAlbumArtist())
                             .setTimestamp(scrobbledSongEntity.getTimestamp())
                             .setAlbumArtUrl("")
                             .setTags(new ArrayList<>())
                             .setWikiContent("")
                             .build();
    }
}
