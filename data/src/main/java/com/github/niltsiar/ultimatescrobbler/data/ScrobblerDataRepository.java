package com.github.niltsiar.ultimatescrobbler.data;

import com.github.niltsiar.ultimatescrobbler.data.mapper.CredentialsMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.PlayedSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.repository.ConfigurationDataStore;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerDataStore;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Single;
import javax.inject.Inject;

public class ScrobblerDataRepository implements ScrobblerRepository {

    private ScrobblerDataStore remoteDataStore;
    private ConfigurationDataStore configurationDataStore;
    private CredentialsMapper credentialsMapper;
    private PlayedSongMapper playedSongMapper;

    @Inject
    public ScrobblerDataRepository(ScrobblerDataStore remoteDataStore,
            ConfigurationDataStore configurationDataStore,
            CredentialsMapper credentialsMapper,
            PlayedSongMapper playedSongMapper) {
        this.remoteDataStore = remoteDataStore;
        this.configurationDataStore = configurationDataStore;
        this.credentialsMapper = credentialsMapper;
        this.playedSongMapper = playedSongMapper;
    }

    @Override
    public Single<String> getMobileSession(Credentials credentials) {
        return remoteDataStore.getMobileSession(credentialsMapper.mapToEntity(credentials))
                              .doOnSuccess(configurationDataStore.saveMobileSession());
    }

    @Override
    public Completable sendNowPlaying(PlayedSong nowPlayingSong) {
        return remoteDataStore.sendNowPlaying(playedSongMapper.mapToEntity(nowPlayingSong));
    }
}
