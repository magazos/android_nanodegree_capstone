package com.github.niltsiar.ultimatescrobbler.data.model;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;
import org.threeten.bp.Instant;

@AutoValue
public abstract class PlayedSongEntity {

    public abstract String getId();

    public abstract String getTrackName();

    public abstract String getArtistName();

    @Nullable
    public abstract String getAlbumName();

    public abstract int getDuration();

    public abstract Instant getTimestamp();

    public static Builder builder() {
        return new AutoValue_PlayedSongEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String newId);

        public abstract Builder setTrackName(String newTrackName);

        public abstract Builder setArtistName(String newArtistName);

        public abstract Builder setAlbumName(String newAlbumName);

        public abstract Builder setDuration(int newDuration);

        public abstract Builder setTimestamp(Instant newTimestamp);

        public abstract PlayedSongEntity build();
    }
}
