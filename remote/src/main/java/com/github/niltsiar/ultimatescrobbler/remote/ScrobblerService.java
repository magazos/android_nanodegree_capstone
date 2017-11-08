package com.github.niltsiar.ultimatescrobbler.remote;

import com.github.niltsiar.ultimatescrobbler.remote.model.ScrobbledSongModel;
import com.serjltt.moshi.adapters.Wrapped;
import io.reactivex.Single;
import java.util.Map;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ScrobblerService {

    String WS_URL = "https://ws.audioscrobbler.com/";
    String WS_PATH = "2.0";

    String QUERY_FORMAT_PARAMETER = "format";

    @FormUrlEncoded
    @POST(WS_PATH)
    @Wrapped(path = {"session", "key"})
    Single<String> requestMobileSessionToken(@FieldMap Map<String, String> parameters, @Query(QUERY_FORMAT_PARAMETER) String format);

    @FormUrlEncoded
    @POST(WS_PATH)
    @Wrapped(path = {"nowplaying"})
    Single<ScrobbledSongModel> updateNowPlaying(@FieldMap Map<String, String> parameters, @Query(QUERY_FORMAT_PARAMETER) String format);

    @FormUrlEncoded
    @POST(WS_PATH)
    @Wrapped(path = {"scrobbles", "scrobble"})
    Single<ScrobbledSongModel> scrobble(@FieldMap Map<String, String> parameters, @Query(QUERY_FORMAT_PARAMETER) String format);
}
