package com.github.niltsiar.ultimatescrobbler.remote;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.github.niltsiar.ultimatescrobbler.data.model.CredentialsEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.ScrobbledSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.ApiKey;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.ApiSecret;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.MobileSessionToken;
import com.github.niltsiar.ultimatescrobbler.remote.services.GetInfoService;
import com.github.niltsiar.ultimatescrobbler.remote.services.ScrobblePlayedSongsService;
import com.github.niltsiar.ultimatescrobbler.remote.services.SendNowPlayingService;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.inject.Inject;
import okio.ByteString;

public class ScrobblerRemoteImpl implements ScrobblerRemote {

    private FirebaseJobDispatcher dispatcher;
    private ScrobblerService scrobblerService;
    private String apiKey;
    private String apiSecret;
    private String mobileSessionToken;

    private static final String RESPONSE_FORMAT = "json";
    private static final String UPDATE_NOW_PLAYING_METHOD_NAME = "track.updateNowPlaying";
    private static final String GET_MOBILE_SESSION_METHOD_NAME = "auth.getMobileSession";
    private static final String SCROBBLE_METHOD_NAME = "track.scrobble";
    private static final String GET_INFO_METHOD_NAME = "track.getInfo";

    private static final String METHOD_PARAM_NAME = "method";
    private static final String ARTIST_PARAM_NAME = "artist";
    private static final String TRACK_PARAM_NAME = "track";
    private static final String TIMESTAMP_PARAM_NAME = "timestamp";
    private static final String ALBUM_PARAM_NAME = "album";
    private static final String DURATION_PARAM_NAME = "duration";
    private static final String API_KEY_PARAM_NAME = "api_key";
    private static final String SESSION_TOKEN_PARAM_NAME = "sk";
    private static final String API_SIGNATURE_PARAM_NAME = "api_sig";
    private static final String USERNAME_PARAM_NAME = "username";
    private static final String PASSWORD_PARAM_NAME = "password";

    @Inject
    public ScrobblerRemoteImpl(FirebaseJobDispatcher dispatcher, ScrobblerService scrobblerService,
            @ApiKey String apiKey,
            @ApiSecret String apiSecret,
            @MobileSessionToken String mobileSessionToken) {
        this.dispatcher = dispatcher;
        this.scrobblerService = scrobblerService;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.mobileSessionToken = mobileSessionToken;
    }

    @Override
    public Single<String> requestMobileSessionToken(CredentialsEntity credentials) {
        SortedMap<String, String> params = new TreeMap<>();
        params.put(METHOD_PARAM_NAME, GET_MOBILE_SESSION_METHOD_NAME);
        params.put(USERNAME_PARAM_NAME, credentials.getUsername());
        params.put(PASSWORD_PARAM_NAME, credentials.getPassword());
        params.put(API_KEY_PARAM_NAME, apiKey);

        String signature = getSignature(params);
        params.put(API_SIGNATURE_PARAM_NAME, signature);

        return scrobblerService.requestMobileSessionToken(params, RESPONSE_FORMAT);
    }

    @Override
    public Completable sendNowPlaying(PlayedSongEntity nowPlayingSong) {
        return Completable.fromAction(() -> {
            SortedMap<String, String> params = createCommonSongApiParams(UPDATE_NOW_PLAYING_METHOD_NAME, nowPlayingSong);

            String signature = getSignature(params);
            params.put(API_SIGNATURE_PARAM_NAME, signature);

            dispatcher.mustSchedule(SendNowPlayingService.createJob(dispatcher, params));
        });
    }

    @Override
    public Completable scrobblePlayedSongs(List<PlayedSongEntity> playedSongs) {
        return Completable.fromObservable(Observable.fromIterable(playedSongs)
                                                    .doOnNext((song -> {
                                                        SortedMap<String, String> params = createCommonSongApiParams(SCROBBLE_METHOD_NAME, song);
                                                        params.put(TIMESTAMP_PARAM_NAME, String.valueOf(song.getTimestamp()
                                                                                                            .getEpochSecond()));

                                                        String signature = getSignature(params);
                                                        params.put(API_SIGNATURE_PARAM_NAME, signature);

                                                        dispatcher.mustSchedule(ScrobblePlayedSongsService.createJob(dispatcher, params));
                                                    })));
    }

    @Override
    public Completable getSongInformation(ScrobbledSongEntity scrobbledSong, String userName) {
        return Completable.fromAction(() -> {
            SortedMap<String, String> params = new TreeMap<>();
            params.put(METHOD_PARAM_NAME, GET_INFO_METHOD_NAME);
            params.put(TRACK_PARAM_NAME, scrobbledSong.getTrackName());
            params.put(ARTIST_PARAM_NAME, scrobbledSong.getArtist());
            params.put(USERNAME_PARAM_NAME, userName);
            params.put(API_KEY_PARAM_NAME, apiKey);

            dispatcher.mustSchedule(GetInfoService.createJob(dispatcher, params, scrobbledSong.getTimeStamp()
                                                                                              .getEpochSecond()));
        });
    }

    private String getSignature(SortedMap<String, String> params) {
        StringBuilder signatureBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            signatureBuilder.append(entry.getKey());
            signatureBuilder.append(entry.getValue());
        }
        signatureBuilder.append(apiSecret);

        return ByteString.encodeUtf8(signatureBuilder.toString())
                         .md5()
                         .hex();
    }

    private SortedMap<String, String> createCommonSongApiParams(String methodName, PlayedSongEntity nowPlayingSong) {
        SortedMap<String, String> params = new TreeMap<>();
        params.put(METHOD_PARAM_NAME, methodName);
        params.put(ARTIST_PARAM_NAME, nowPlayingSong.getArtistName());
        params.put(TRACK_PARAM_NAME, nowPlayingSong.getTrackName());
        params.put(ALBUM_PARAM_NAME, nowPlayingSong.getAlbumName());
        params.put(DURATION_PARAM_NAME, String.valueOf(nowPlayingSong.getDuration()));
        params.put(API_KEY_PARAM_NAME, apiKey);
        params.put(SESSION_TOKEN_PARAM_NAME, mobileSessionToken);
        return params;
    }
}
