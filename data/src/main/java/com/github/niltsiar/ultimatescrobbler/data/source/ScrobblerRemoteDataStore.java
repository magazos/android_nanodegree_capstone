package com.github.niltsiar.ultimatescrobbler.data.source;

import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerDataStore;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import io.reactivex.Completable;
import javax.inject.Inject;

public class ScrobblerRemoteDataStore implements ScrobblerDataStore {

    private ScrobblerRemote scrobblerRemote;

    @Inject
    public ScrobblerRemoteDataStore(ScrobblerRemote scrobblerRemote) {
        this.scrobblerRemote = scrobblerRemote;
    }

    @Override
    public Completable sendNowPlaying(PlayedSongEntity nowPlayingSong) {
        return scrobblerRemote.sendNowPlaying(nowPlayingSong);
    }
}
