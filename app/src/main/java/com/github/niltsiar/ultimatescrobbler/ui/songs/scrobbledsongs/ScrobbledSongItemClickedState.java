package com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs;

import android.view.View;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;
import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ScrobbledSongItemClickedState {

    public abstract InfoSong getInfoSong();

    public abstract View getSongTitleView();

    public abstract View getSongArtistView();

    public abstract View getAlbumArtView();

    public static ScrobbledSongItemClickedState.Builder builder() {
        return new AutoValue_ScrobbledSongItemClickedState.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setInfoSong(InfoSong newInfoSong);

        public abstract Builder setSongTitleView(View newSongTitleView);

        public abstract Builder setSongArtistView(View newSongArtistView);

        public abstract Builder setAlbumArtView(View newAlbumArtView);

        public abstract ScrobbledSongItemClickedState build();
    }
}
