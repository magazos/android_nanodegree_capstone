package com.github.niltsiar.ultimatescrobbler.data.repository;

import com.github.niltsiar.ultimatescrobbler.data.model.CredentialsEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.InfoSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.ScrobbledSongEntity;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;

public interface ScrobblerRemote {

    Single<String> requestMobileSessionToken(CredentialsEntity credentials);

    Single<ScrobbledSongEntity> sendNowPlaying(PlayedSongEntity nowPlayingSong);

    Observable<ScrobbledSongEntity> scrobblePlayedSongs(List<PlayedSongEntity> playedSongs);

    Single<InfoSongEntity> requestSongInformation(ScrobbledSongEntity scrobbledSong);
}
