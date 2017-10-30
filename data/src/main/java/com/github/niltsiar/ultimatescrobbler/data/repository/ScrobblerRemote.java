package com.github.niltsiar.ultimatescrobbler.data.repository;

import com.github.niltsiar.ultimatescrobbler.data.model.CredentialsEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface ScrobblerRemote {

    Single<String> requestMobileSessionToken(CredentialsEntity credentials);

    Completable sendNowPlaying(PlayedSongEntity nowPlayingSong);

}
