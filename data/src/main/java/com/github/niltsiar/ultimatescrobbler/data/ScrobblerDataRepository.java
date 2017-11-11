package com.github.niltsiar.ultimatescrobbler.data;

import com.github.niltsiar.ultimatescrobbler.data.mapper.CredentialsMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.PlayedSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.ScrobbledSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.repository.ConfigurationCache;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.data.repository.SongsCache;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.model.ScrobbledSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;

public class ScrobblerDataRepository implements ScrobblerRepository {

    private Provider<ScrobblerRemote> scrobblerRemote;
    private ConfigurationCache configurationCache;
    private SongsCache songsCache;
    private CredentialsMapper credentialsMapper;
    private PlayedSongMapper playedSongMapper;
    private ScrobbledSongMapper scrobbledSongMapper;

    @Inject
    public ScrobblerDataRepository(Provider<ScrobblerRemote> scrobblerRemote, ConfigurationCache configurationCache, SongsCache songsCache,
            CredentialsMapper credentialsMapper,
            PlayedSongMapper playedSongMapper,
            ScrobbledSongMapper scrobbledSongMapper) {
        this.scrobblerRemote = scrobblerRemote;
        this.configurationCache = configurationCache;
        this.songsCache = songsCache;
        this.credentialsMapper = credentialsMapper;
        this.playedSongMapper = playedSongMapper;
        this.scrobbledSongMapper = scrobbledSongMapper;
    }

    @Override
    public Single<String> requestMobileSessionToken(Credentials credentials) {
        return scrobblerRemote.get()
                              .requestMobileSessionToken(credentialsMapper.mapToEntity(credentials))
                              .doOnSuccess(configurationCache.saveMobileSessionToken());
    }

    @Override
    public Completable sendNowPlaying(PlayedSong nowPlayingSong) {
        return scrobblerRemote.get()
                              .sendNowPlaying(playedSongMapper.mapToEntity(nowPlayingSong));
    }

    @Override
    public Completable savePlayedSong(PlayedSong playedSong) {
        return songsCache.savePlayedSong(playedSongMapper.mapToEntity(playedSong));
    }

    @Override
    public Single<Long> countStoredPlayedSongs() {
        return songsCache.countStoredPlayedSongs();
    }

    @Override
    public Single<List<PlayedSong>> getStoredPlayedSongs() {
        return songsCache.getStoredPlayedSongs()
                         .flatMapObservable(Observable::fromIterable)
                         .map(playedSongMapper::mapFromEntity)
                         .toList();
    }

    @Override
    public Completable scrobblePlayedSongs(List<PlayedSong> playedSongs) {
        return scrobblerRemote.get()
                              .scrobblePlayedSongs(Observable.fromIterable(playedSongs)
                                                             .map(song -> playedSongMapper.mapToEntity(song))
                                                             .toList()
                                                             .blockingGet());
    }

    @Override
    public Completable getSongInformation(ScrobbledSong song, String username) {
        return scrobblerRemote.get()
                              .requestSongInformation(scrobbledSongMapper.mapToEntity(song), username);
    }
}
