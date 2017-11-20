package com.github.niltsiar.ultimatescrobbler.data.repository;

import com.github.niltsiar.ultimatescrobbler.data.model.InfoSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;

public interface SongsCache {

    Completable savePlayedSong(PlayedSongEntity playedSongEntity);

    Single<Long> countStoredPlayedSongs();

    Single<PlayedSongEntity> getStoredPlayedSong(String id);

    Single<List<PlayedSongEntity>> getStoredPlayedSongs();

    Completable markSongAsScrobbled(PlayedSongEntity playedSongEntity);

    Completable saveSongInformation(InfoSongEntity infoSongEntity);
}
