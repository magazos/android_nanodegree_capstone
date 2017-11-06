package com.github.niltsiar.ultimatescrobbler.domain.repository;

import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface ScrobblerRepository {

    Single<String> requestMobileSessionToken(Credentials credentials);

    Completable sendNowPlaying(PlayedSong nowPlayingSong);

    Completable savePlayedSong(PlayedSong playedSong);
}
