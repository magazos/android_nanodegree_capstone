package com.github.niltsiar.ultimatescrobbler.domain.repository;

import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.model.ScrobbledSong;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;

public interface ScrobblerRepository {

    Single<String> requestMobileSessionToken(Credentials credentials);

    Completable sendNowPlaying(PlayedSong nowPlayingSong);

    Completable savePlayedSong(PlayedSong playedSong);

    Single<Long> countStoredPlayedSongs();

    Single<PlayedSong> getStoredPlayedSong(String songId);

    Single<List<PlayedSong>> getStoredPlayedSongs();

    Observable<ScrobbledSong> scrobblePlayedSongs(List<PlayedSong> playedSongs);

    Single<InfoSong> getSongInformation(ScrobbledSong song);

    Completable markSongAsScrobbled(PlayedSong playedSong);

    Completable saveSongInformation(InfoSong infoSong);

    Completable deleteStoredPlayedSong(PlayedSong playedSong);
}
