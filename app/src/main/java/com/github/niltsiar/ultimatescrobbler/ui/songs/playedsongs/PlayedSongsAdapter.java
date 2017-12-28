package com.github.niltsiar.ultimatescrobbler.ui.songs.playedsongs;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.cache.database.PlayedSongColumns;

public class PlayedSongsAdapter extends RecyclerView.Adapter<PlayedSongItemViewHolder> {

    private Cursor cursor;

    public PlayedSongsAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public PlayedSongItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_played_song, parent, false);
        return new PlayedSongItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayedSongItemViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String title = cursor.getString(PlayedSongColumns.Query.Index.TRACK_NAME);
        String artist = cursor.getString(PlayedSongColumns.Query.Index.ARTIST_NAME);
        String date = cursor.getString(PlayedSongColumns.Query.Index.TIMESTAMP);
        holder.bind(title, artist, date);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
