package com.github.niltsiar.ultimatescrobbler.domain.model;

import com.google.auto.value.AutoValue;
import java.util.List;
import org.threeten.bp.Instant;

@AutoValue
public abstract class InfoSong {

    public abstract String getTrackName();

    public abstract String getArtist();

    public abstract String getAlbum();

    public abstract String getAlbumArtist();

    public abstract String getAlbumArtUrl();

    public abstract List<String> getTags();

    public abstract Instant getTimestamp();

    public abstract String getWikiContent();

    public static Builder builder() {
        return new AutoValue_InfoSong.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setTrackName(String newTrackName);

        public abstract Builder setArtist(String newArtist);

        public abstract Builder setAlbum(String newAlbum);

        public abstract Builder setAlbumArtist(String newAlbumArtist);

        public abstract Builder setAlbumArtUrl(String newAlbumArtUrl);

        public abstract Builder setTags(List<String> newTags);

        public abstract Builder setTimestamp(Instant newTimestamp);

        public abstract Builder setWikiContent(String newWikiContent);

        public abstract InfoSong build();
    }
}
