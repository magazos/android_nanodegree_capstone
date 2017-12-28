package com.github.niltsiar.ultimatescrobbler.ui.songs.playedsongs;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.niltsiar.ultimatescrobbler.R;

public class PlayedSongItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.played_song_title)
    TextView playedSongTitle;
    @BindView(R.id.played_song_artist)
    TextView playedSongArtist;
    @BindView(R.id.played_song_date)
    TextView playedSongDate;

    public PlayedSongItemViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(String title, String artist, String date) {
        playedSongTitle.setText(title);
        playedSongArtist.setText(artist);
        playedSongDate.setText(date);
    }
}
