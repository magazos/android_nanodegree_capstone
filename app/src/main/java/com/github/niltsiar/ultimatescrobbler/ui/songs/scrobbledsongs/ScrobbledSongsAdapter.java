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
import com.jakewharton.rxrelay2.PublishRelay;
import io.reactivex.Observable;

interface OnItemClickListener {
    void onClickedItem(InfoSong infoSong, View songTitleView, View songArtistView, View albumArtView);
}

public class ScrobbledSongsAdapter extends RecyclerView.Adapter<ScrobbledSongItemViewHolder> implements OnItemClickListener {

    private Cursor cursor;
    private InfoSong infoSong;
    private PublishRelay<ScrobbledSongItemClickedState> clickedItems;

    public ScrobbledSongsAdapter(Cursor cursor) {
        this.cursor = cursor;
        clickedItems = PublishRelay.create();
    }

    @Override
    public ScrobbledSongItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_scrobbled_song, parent, false);
        return new ScrobbledSongItemViewHolder(view, this);
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

    @Override
    public void onClickedItem(InfoSong infoSong, View songTitleView, View songArtistView, View albumArtView) {
        ScrobbledSongItemClickedState clickedState = ScrobbledSongItemClickedState.builder()
                                                                                  .setInfoSong(infoSong)
                                                                                  .setSongTitleView(songTitleView)
                                                                                  .setSongArtistView(songArtistView)
                                                                                  .setAlbumArtView(albumArtView)
                                                                                  .build();
        clickedItems.accept(clickedState);
    }

    public Observable<ScrobbledSongItemClickedState> getClickedItem() {
        return clickedItems;
    }
}
