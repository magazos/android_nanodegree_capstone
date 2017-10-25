package com.github.niltsiar.ultimatescrobbler.domain.repository;

import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface ScrobblerRepository {

    Single<String> getMobileSession(Credentials credentials);

    Completable sendNowPlaying(PlayedSong nowPlayingSong);
}
