package com.github.niltsiar.ultimatescrobbler.data;

import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;

public class ScrobblerDataRepository implements ScrobblerRepository {
    @Override
    public Completable sendNowPlaying(PlayedSong nowPlayingSong) {
        return null;
    }
}
