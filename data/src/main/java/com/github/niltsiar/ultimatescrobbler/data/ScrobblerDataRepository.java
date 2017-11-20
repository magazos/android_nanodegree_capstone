package com.github.niltsiar.ultimatescrobbler.data;

import com.github.niltsiar.ultimatescrobbler.data.mapper.CredentialsMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.InfoSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.PlayedSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.mapper.ScrobbledSongMapper;
import com.github.niltsiar.ultimatescrobbler.data.repository.ConfigurationCache;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.data.repository.SongsCache;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;
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
    private InfoSongMapper infoSongMapper;

    @Inject
    public ScrobblerDataRepository(Provider<ScrobblerRemote> scrobblerRemote,
            ConfigurationCache configurationCache,
            SongsCache songsCache,
            CredentialsMapper credentialsMapper,
            PlayedSongMapper playedSongMapper,
            ScrobbledSongMapper scrobbledSongMapper,
            InfoSongMapper infoSongMapper) {
        this.scrobblerRemote = scrobblerRemote;
        this.configurationCache = configurationCache;
        this.songsCache = songsCache;
        this.credentialsMapper = credentialsMapper;
        this.playedSongMapper = playedSongMapper;
        this.scrobbledSongMapper = scrobbledSongMapper;
        this.infoSongMapper = infoSongMapper;
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
                              .sendNowPlaying(playedSongMapper.mapToEntity(nowPlayingSong))
                              .toCompletable();
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
    public Single<PlayedSong> getStoredPlayedSong(String songId) {
        return songsCache.getStoredPlayedSong(songId)
                         .map(playedSongMapper::mapFromEntity);
    }

    @Override
    public Single<List<PlayedSong>> getStoredPlayedSongs() {
        return songsCache.getStoredPlayedSongs()
                         .flatMapObservable(Observable::fromIterable)
                         .map(playedSongMapper::mapFromEntity)
                         .toList();
    }

    @Override
    public Observable<ScrobbledSong> scrobblePlayedSongs(List<PlayedSong> playedSongs) {
        return scrobblerRemote.get()
                              .scrobblePlayedSongs(Observable.fromIterable(playedSongs)
                                                             .map(song -> playedSongMapper.mapToEntity(song))
                                                             .toList()
                                                             .blockingGet())
                              .map(scrobbledSongMapper::mapFromEntity);
    }

    @Override
    public Single<InfoSong> getSongInformation(ScrobbledSong song) {
        return scrobblerRemote.get()
                              .requestSongInformation(scrobbledSongMapper.mapToEntity(song))
                              .map(infoSongMapper::mapFromEntity);
    }

    @Override
    public Completable markSongAsScrobbled(PlayedSong playedSong) {
        return songsCache.markSongAsScrobbled(playedSongMapper.mapToEntity(playedSong));
    }
}
