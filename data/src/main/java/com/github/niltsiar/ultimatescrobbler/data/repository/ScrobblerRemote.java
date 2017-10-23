package com.github.niltsiar.ultimatescrobbler.data.repository;

import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import io.reactivex.Completable;

public interface ScrobblerRemote {

    Completable sendNowPlaying(PlayedSongEntity nowPlayingSong);

}
