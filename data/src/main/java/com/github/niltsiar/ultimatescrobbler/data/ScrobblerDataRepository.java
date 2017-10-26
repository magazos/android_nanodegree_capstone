package com.github.niltsiar.ultimatescrobbler.data;

import com.github.niltsiar.ultimatescrobbler.data.mapper.CredentialsMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.PlayedSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerDataStore;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Single;
import javax.inject.Inject;

public class ScrobblerDataRepository implements ScrobblerRepository {

    private ScrobblerDataStore remoteDataStore;
    private CredentialsMapper credentialsMapper;
    private PlayedSongMapper playedSongMapper;

    @Inject
    public ScrobblerDataRepository(ScrobblerDataStore remoteDataStore, CredentialsMapper credentialsMapper, PlayedSongMapper playedSongMapper) {
        this.remoteDataStore = remoteDataStore;
        this.credentialsMapper = credentialsMapper;
        this.playedSongMapper = playedSongMapper;
    }

    @Override
    public Single<String> getMobileSession(Credentials credentials) {
        return remoteDataStore.getMobileSession(credentialsMapper.mapToEntity(credentials));
    }

    @Override
    public Completable sendNowPlaying(PlayedSong nowPlayingSong) {
        return remoteDataStore.sendNowPlaying(playedSongMapper.mapToEntity(nowPlayingSong));
    }
}
