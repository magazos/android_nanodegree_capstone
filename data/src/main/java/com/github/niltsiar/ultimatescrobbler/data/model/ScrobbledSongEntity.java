package com.github.niltsiar.ultimatescrobbler.data.model;

import com.google.auto.value.AutoValue;
import java.util.UUID;
import org.threeten.bp.Instant;

@AutoValue
public abstract class ScrobbledSongEntity {

    public abstract String getId();

    public abstract String getTrackName();

    public abstract String getArtist();

    public abstract String getAlbum();

    public abstract String getAlbumArtist();

    public abstract Instant getTimestamp();

    abstract Builder toBuilder();

    public ScrobbledSongEntity withId(String newId) {
        return toBuilder().setId(newId)
                          .build();
    }

    public static Builder builder() {
        return new AutoValue_ScrobbledSongEntity.Builder().setId(UUID.randomUUID()
                                                                     .toString());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String newId);

        public abstract Builder setTrackName(String newTrackName);

        public abstract Builder setArtist(String newArtist);

        public abstract Builder setAlbum(String newAlbum);

        public abstract Builder setAlbumArtist(String newAlbumArtist);

        public abstract Builder setTimestamp(Instant newTimestamp);

        public abstract ScrobbledSongEntity build();
    }
}
