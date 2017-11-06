package com.github.niltsiar.ultimatescrobbler.data;

import com.github.niltsiar.ultimatescrobbler.data.mapper.CredentialsMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.PlayedSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.repository.ConfigurationCache;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.data.repository.SongsCache;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Single;
import javax.inject.Inject;

public class ScrobblerDataRepository implements ScrobblerRepository {

    private ScrobblerRemote scrobblerRemote;
    private ConfigurationCache configurationCache;
    private SongsCache songsCache;
    private CredentialsMapper credentialsMapper;
    private PlayedSongMapper playedSongMapper;

    @Inject
    public ScrobblerDataRepository(ScrobblerRemote scrobblerRemote, ConfigurationCache configurationCache, SongsCache songsCache,
            CredentialsMapper credentialsMapper,
            PlayedSongMapper playedSongMapper) {
        this.scrobblerRemote = scrobblerRemote;
        this.configurationCache = configurationCache;
        this.songsCache = songsCache;
        this.credentialsMapper = credentialsMapper;
        this.playedSongMapper = playedSongMapper;
    }

    @Override
    public Single<String> requestMobileSessionToken(Credentials credentials) {
        return scrobblerRemote.requestMobileSessionToken(credentialsMapper.mapToEntity(credentials))
                              .doOnSuccess(configurationCache.saveMobileSessionToken());
    }

    @Override
    public Completable sendNowPlaying(PlayedSong nowPlayingSong) {
        return scrobblerRemote.sendNowPlaying(playedSongMapper.mapToEntity(nowPlayingSong));
    }

    @Override
    public Completable savePlayedSong(PlayedSong playedSong) {
        return songsCache.savePlayedSong(playedSongMapper.mapToEntity(playedSong));
    }
}
