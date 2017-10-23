package com.github.niltsiar.ultimatescrobbler.remote;

import com.github.niltsiar.ultimatescrobbler.data.model.PlayedSongEntity;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import io.reactivex.Completable;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.inject.Inject;
import okio.ByteString;

public class ScrobblerRemoteImpl implements ScrobblerRemote {

    private ScrobblerService scrobblerService;

    private static final String RESPONSE_FORMAT = "json";
    private static final String UPDATE_NOW_PLAYING_METHOD_NAME = "track.updateNowPlaying";

    @Inject
    public ScrobblerRemoteImpl(ScrobblerService scrobblerService) {
        this.scrobblerService = scrobblerService;
    }

    @Override
    public Completable sendNowPlaying(final PlayedSongEntity nowPlayingSong) {
        final SortedMap<String, String> params = new TreeMap<>();
        params.put("method", UPDATE_NOW_PLAYING_METHOD_NAME);
        params.put("artist", nowPlayingSong.getArtistName());
        params.put("track", nowPlayingSong.getTrackName());
        params.put("album", nowPlayingSong.getAlbumName());
        //params.put("duration", nowPlayingSong.getDuration());
        //params.put("api_key", API_KEY);
        //params.put("sk", AUTH);

        final String signature = getSignature(params);
        params.put("api_sig", signature);

        return scrobblerService.updateNowPlaying(params, RESPONSE_FORMAT)
                               .toCompletable();
    }

    private String getSignature(SortedMap<String, String> params) {
        StringBuilder signatureBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            signatureBuilder.append(entry.getKey());
            signatureBuilder.append(entry.getValue());
        }
        //signatureBuilder.append(API_SECRET);

        return ByteString.encodeUtf8(signatureBuilder.toString())
                         .md5()
                         .hex();
    }
}
