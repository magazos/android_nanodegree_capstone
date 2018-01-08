package com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.cache.database.InfoSongColumns;

public class ScrobbledSongsAdapter extends RecyclerView.Adapter<ScrobbledSongItemViewHolder> {

    private Cursor cursor;

    public ScrobbledSongsAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public ScrobbledSongItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_scrobbled_song, parent, false);
        return new ScrobbledSongItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScrobbledSongItemViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String title = cursor.getString(InfoSongColumns.Query.Index.TRACK_NAME);
        String artist = cursor.getString(InfoSongColumns.Query.Index.ARTIST_NAME);
        String albumArt = cursor.getString(InfoSongColumns.Query.Index.ALBUM_ART_URL);
        holder.bind(title, artist, "00/00/0000 00:00", albumArt);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
