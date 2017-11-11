package com.github.niltsiar.ultimatescrobbler.domain.repository;

import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.model.ScrobbledSong;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;

public interface ScrobblerRepository {

    Single<String> requestMobileSessionToken(Credentials credentials);

    Completable sendNowPlaying(PlayedSong nowPlayingSong);

    Completable savePlayedSong(PlayedSong playedSong);

    Single<Long> countStoredPlayedSongs();

    Single<List<PlayedSong>> getStoredPlayedSongs();

    Completable scrobblePlayedSongs(List<PlayedSong> playedSongs);

    Completable getSongInformation(ScrobbledSong song, String username);
}
