package com.github.niltsiar.ultimatescrobbler.domain.repository;

import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import io.reactivex.Completable;

public interface ScrobblerRepository {

    Completable sendNowPlaying(PlayedSong nowPlayingSong);
}
