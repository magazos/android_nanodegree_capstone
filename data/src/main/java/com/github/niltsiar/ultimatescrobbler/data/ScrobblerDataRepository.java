package com.github.niltsiar.ultimatescrobbler.data;

import com.github.niltsiar.ultimatescrobbler.data.mapper.PlayedSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.source.ScrobblerRemoteDataStore;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import javax.inject.Inject;

public class ScrobblerDataRepository implements ScrobblerRepository {

    private ScrobblerRemoteDataStore remoteDataStore;
    private PlayedSongMapper playedSongMapper;

    @Inject
    public ScrobblerDataRepository(ScrobblerRemoteDataStore remoteDataStore, PlayedSongMapper playedSongMapper) {
        this.remoteDataStore = remoteDataStore;
        this.playedSongMapper = playedSongMapper;
    }

    @Override
    public Completable sendNowPlaying(PlayedSong nowPlayingSong) {
        return remoteDataStore.sendNowPlaying(playedSongMapper.mapToEntity(nowPlayingSong));
    }
}
