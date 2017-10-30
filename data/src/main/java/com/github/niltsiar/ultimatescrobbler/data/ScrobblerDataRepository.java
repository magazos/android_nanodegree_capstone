package com.github.niltsiar.ultimatescrobbler.data;

import com.github.niltsiar.ultimatescrobbler.data.mapper.CredentialsMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.PlayedSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.repository.ConfigurationCache;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Single;
import javax.inject.Inject;

public class ScrobblerDataRepository implements ScrobblerRepository {

    private ScrobblerRemote scrobblerRemote;
    private ConfigurationCache configurationDataStore;
    private CredentialsMapper credentialsMapper;
    private PlayedSongMapper playedSongMapper;

    @Inject
    public ScrobblerDataRepository(ScrobblerRemote scrobblerRemote, ConfigurationCache configurationDataStore,
            CredentialsMapper credentialsMapper,
            PlayedSongMapper playedSongMapper) {
        this.scrobblerRemote = scrobblerRemote;
        this.configurationDataStore = configurationDataStore;
        this.credentialsMapper = credentialsMapper;
        this.playedSongMapper = playedSongMapper;
    }

    @Override
    public Single<String> requestMobileSessionToken(Credentials credentials) {
        return scrobblerRemote.requestMobileSessionToken(credentialsMapper.mapToEntity(credentials))
                              .doOnSuccess(configurationDataStore.saveMobileSessionToken());
    }

    @Override
    public Completable sendNowPlaying(PlayedSong nowPlayingSong) {
        return scrobblerRemote.sendNowPlaying(playedSongMapper.mapToEntity(nowPlayingSong));
    }
}
