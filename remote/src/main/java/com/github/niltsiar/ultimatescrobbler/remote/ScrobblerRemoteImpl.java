package com.github.niltsiar.ultimatescrobbler.remote;

import com.github.niltsiar.ultimatescrobbler.data.model.CredentialsEntity;
import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.ApiKey;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.ApiSecret;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.MobileSessionToken;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.inject.Inject;
import okio.ByteString;
import timber.log.Timber;

public class ScrobblerRemoteImpl implements ScrobblerRemote {

    private ScrobblerService scrobblerService;
    private String apiKey;
    private String apiSecret;
    private String mobileSessionToken;

    private static final String RESPONSE_FORMAT = "json";
    private static final String UPDATE_NOW_PLAYING_METHOD_NAME = "track.updateNowPlaying";
    private static final String GET_MOBILE_SESSION_METHOD_NAME = "auth.getMobileSession";

    @Inject
    public ScrobblerRemoteImpl(ScrobblerService scrobblerService,
            @ApiKey String apiKey,
            @ApiSecret String apiSecret,
            @MobileSessionToken String mobileSessionToken) {
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
    public Completable sendNowPlaying(final PlayedSongEntity nowPlayingSong) {
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

        return scrobblerService.updateNowPlaying(params, RESPONSE_FORMAT)
                               .doOnSuccess(song -> Timber.i(song.toString()))
                               .toCompletable();
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
}
