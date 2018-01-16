package com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs;

import android.content.Context;
import android.content.Intent;
import com.github.niltsiar.ultimatescrobbler.cache.database.SongsProvider;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;
import javax.inject.Inject;

public class ScrobbledSongItemClickListener implements ScrobbledSongsAdapter.OnItemClickListener {

    private Context context;

    @Inject
    public ScrobbledSongItemClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClickedItem(InfoSong infoSong) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, SongsProvider.InfoSong.withId(String.valueOf(infoSong.getTimestamp()
                                                                                                                  .toEpochMilli()))));
    }
}
