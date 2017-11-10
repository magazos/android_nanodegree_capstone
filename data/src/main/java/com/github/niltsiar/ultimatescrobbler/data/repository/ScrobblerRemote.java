package com.github.niltsiar.ultimatescrobbler.data.repository;

import com.github.niltsiar.ultimatescrobbler.data.model.CredentialsEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.ScrobbledSongEntity;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;

public interface ScrobblerRemote {

    Single<String> requestMobileSessionToken(CredentialsEntity credentials);

    Completable sendNowPlaying(PlayedSongEntity nowPlayingSong);

    Completable scrobblePlayedSongs(List<PlayedSongEntity> playedSongs);

    Completable getSongInformation(ScrobbledSongEntity scrobbledSong, String username);
}
