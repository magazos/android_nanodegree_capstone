package com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.niltsiar.ultimatescrobbler.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class ScrobbledSongItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.scrobbled_song_title)
    TextView scrobbledSongTitle;
    @BindView(R.id.scrobbled_song_artist)
    TextView scrobbledSongArtist;
    @BindView(R.id.scrobbled_song_date)
    TextView scrobbledSongDate;
    @BindView(R.id.scrobbled_song_album_art)
    ImageView scrobbledSongAlbumArt;

    public ScrobbledSongItemViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(String title, String artist, String date, String albumArt) {
        scrobbledSongTitle.setText(title);
        scrobbledSongArtist.setText(artist);
        scrobbledSongDate.setText(date);
        Picasso picasso = Picasso.with(scrobbledSongAlbumArt.getContext());
        RequestCreator request;
        if (!TextUtils.isEmpty(albumArt)) {
            request = picasso.load(albumArt);
        } else {
            request = picasso.load(R.drawable.ic_note);
        }
        request.placeholder(R.drawable.ic_note)
               .error(R.drawable.ic_note)
               .into(scrobbledSongAlbumArt);
    }
}
