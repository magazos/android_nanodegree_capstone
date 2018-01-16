package com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;
import com.squareup.picasso.Picasso;

public class ScrobbledSongItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.scrobbled_song_title)
    TextView scrobbledSongTitle;
    @BindView(R.id.scrobbled_song_artist)
    TextView scrobbledSongArtist;
    @BindView(R.id.scrobbled_song_album_art)
    ImageView scrobbledSongAlbumArt;

    private ScrobbledSongsAdapter.OnItemClickListener listener;
    private InfoSong infoSong;

    public ScrobbledSongItemViewHolder(View itemView, ScrobbledSongsAdapter.OnItemClickListener listener) {
        super(itemView);
        this.listener = listener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(InfoSong infoSong) {
        this.infoSong = infoSong;
        scrobbledSongTitle.setText(infoSong.getTrackName());
        scrobbledSongArtist.setText(infoSong.getArtist());
        if (!TextUtils.isEmpty(infoSong.getAlbumArtUrl())) {
            Picasso.with(scrobbledSongAlbumArt.getContext())
                   .load(infoSong.getAlbumArtUrl())
                   .placeholder(R.drawable.ic_note)
                   .error(R.drawable.ic_note)
                   .into(scrobbledSongAlbumArt);
        }
        ViewCompat.setTransitionName(scrobbledSongAlbumArt, String.valueOf(infoSong.getTimestamp()
                                                                                   .toEpochMilli()));
    }

    @OnClick(R.id.scrobbled_song_item)
    public void onClick() {
        listener.onClickedItem(infoSong, scrobbledSongAlbumArt);
    }
}
