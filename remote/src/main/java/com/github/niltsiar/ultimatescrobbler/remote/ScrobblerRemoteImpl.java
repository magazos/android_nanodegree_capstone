package com.github.niltsiar.ultimatescrobbler.remote;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.github.niltsiar.ultimatescrobbler.data.model.CredentialsEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.ApiKey;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.ApiSecret;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.MobileSessionToken;
import com.github.niltsiar.ultimatescrobbler.remote.services.ScrobblePlayedSongsService;
import com.github.niltsiar.ultimatescrobbler.remote.services.SendNowPlayingService;
import io.reactivex.Completable;
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

    private static final String METHOD_PARAM_NAME = "method";
    private static final String ARTIST_PARAM_NAME = "artist";
    private static final String TRACK_PARAM_NAME = "track";
    private static final String TIMESTAMP_PARAM_NAME = "timestamp";
    private static final String ALBUM_PARAM_NAME = "album";
    private static final String DURATION_PARAM_NAME = "duration";
    private static final String API_KEY_PARAM_NAME = "api_key";
    private static final String SESSION_TOKEN_PARAM_NAME = "sk";
    private static final String API_SIGNATURE_PARAM_NAME = "api_sig";

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
        params.put("method", GET_MOBILE_SESSION_METHOD_NAME);
        params.put("username", credentials.getUsername());
        params.put("password", credentials.getPassword());
        params.put("api_key", apiKey);

        String signature = getSignature(params);
        params.put("api_sig", signature);

        return scrobblerService.requestMobileSessionToken(params, RESPONSE_FORMAT);
    }

    @Override
    public Completable sendNowPlaying(PlayedSongEntity nowPlayingSong) {
        return Completable.fromAction(() -> {
            SortedMap<String, String> params = new TreeMap<>();
            params.put("method", UPDATE_NOW_PLAYING_METHOD_NAME);
            params.put("artist", nowPlayingSong.getArtistName());
            params.put("track", nowPlayingSong.getTrackName());
            params.put("album", nowPlayingSong.getAlbumName());
            params.put("duration", String.valueOf(nowPlayingSong.getDuration()));
            params.put("api_key", apiKey);
            params.put("sk", mobileSessionToken);

            String signature = getSignature(params);
            params.put("api_sig", signature);

            dispatcher.mustSchedule(SendNowPlayingService.createJob(dispatcher, params));
        });
    }

    @Override
    public Completable scrobblePlayedSongs(List<PlayedSongEntity> playedSongs) {
        return Completable.fromAction(() -> {
            SortedMap<String, String> params = new TreeMap<>();
            params.put(METHOD_PARAM_NAME, SCROBBLE_METHOD_NAME);

            int listSize = playedSongs.size();
            for (int index = 0; index < listSize; index++) {
                PlayedSongEntity song = playedSongs.get(index);
                params.put(createIndexedParamName(ARTIST_PARAM_NAME, index), song.getArtistName());
                params.put(createIndexedParamName(TRACK_PARAM_NAME, index), song.getTrackName());
                params.put(createIndexedParamName(TIMESTAMP_PARAM_NAME, index), String.valueOf(song.getTimestamp()));
                params.put(createIndexedParamName(ALBUM_PARAM_NAME, index), song.getAlbumName());
                params.put(createIndexedParamName(DURATION_PARAM_NAME, index), String.valueOf(song.getDuration()));
            }
            params.put(API_KEY_PARAM_NAME, apiKey);
            params.put(SESSION_TOKEN_PARAM_NAME, mobileSessionToken);

            String signature = getSignature(params);
            params.put(API_SIGNATURE_PARAM_NAME, signature);

            dispatcher.mustSchedule(ScrobblePlayedSongsService.createJob(dispatcher, params));
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

    private static String createIndexedParamName(String paramName, int index) {
        return paramName + "[" + index + "]";
    }
}
