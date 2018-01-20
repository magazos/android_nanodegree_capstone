package com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.cache.mapper.InfoSongEntityMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.InfoSongMapper;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;

public class ScrobbledSongsAdapter extends RecyclerView.Adapter<ScrobbledSongItemViewHolder> {

    private Cursor cursor;
    private InfoSong infoSong;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClickedItem(InfoSong infoSong, View songTitleView, View songArtistView, View albumArtView);
    }

    public ScrobbledSongsAdapter(Cursor cursor, OnItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    @Override
    public ScrobbledSongItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_scrobbled_song, parent, false);
        return new ScrobbledSongItemViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ScrobbledSongItemViewHolder holder, int position) {
        cursor.moveToPosition(position);
        InfoSongMapper mapper = new InfoSongMapper();
        infoSong = mapper.mapFromEntity(InfoSongEntityMapper.mapFromCache(cursor));
        holder.bind(infoSong);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
