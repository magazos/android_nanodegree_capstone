package com.github.niltsiar.ultimatescrobbler.domain.model;

import com.google.auto.value.AutoValue;
import java.util.UUID;
import javax.annotation.Nullable;

@AutoValue
public abstract class PlayedSong {

    public abstract String getId();

    public abstract String getTrackName();

    public abstract String getArtistName();

    @Nullable
    public abstract String getAlbumName();

    public abstract int getLength();

    public static Builder builder() {
        return new AutoValue_PlayedSong.Builder().setId(UUID.randomUUID()
                                                            .toString());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        abstract Builder setId(String newId);

        public abstract Builder setTrackName(String newTrackName);

        public abstract Builder setArtistName(String newArtistName);

        public abstract Builder setAlbumName(@Nullable String newAlbumName);

        public abstract Builder setLength(int newLength);

        public abstract PlayedSong build();
    }
}
