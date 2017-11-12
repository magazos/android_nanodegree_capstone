package com.github.niltsiar.ultimatescrobbler.data.model;

import com.google.auto.value.AutoValue;
import java.util.List;
import org.threeten.bp.Instant;

@AutoValue
public abstract class InfoSongEntity {

    public abstract String getTrackName();

    public abstract String getArtist();

    public abstract String getAlbum();

    public abstract String getAlbumArtist();

    public abstract String getAlbumArtUrl();

    public abstract List<String> getTags();

    public abstract Instant getTimestamp();

    public abstract String getWikiContent();

    abstract Builder toBuilder();

    public InfoSongEntity withTimestamp(Instant newTimestamp) {
        return toBuilder().setTimestamp(newTimestamp)
                          .build();
    }

    public static Builder builder() {
        return new AutoValue_InfoSongEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTrackName(String newTrackName);

        public abstract Builder setArtist(String newArtis);

        public abstract Builder setAlbum(String newAlbum);

        public abstract Builder setAlbumArtist(String newAlbumArtis);

        public abstract Builder setAlbumArtUrl(String newAlbumArtUrl);

        public abstract Builder setTags(List<String> newTags);

        public abstract Builder setTimestamp(Instant newTimestamp);

        public abstract Builder setWikiContent(String newWikiContent);

        public abstract InfoSongEntity build();
    }
}
