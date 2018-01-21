package com.github.niltsiar.ultimatescrobbler.remote.model;

import com.google.auto.value.AutoValue;
import com.serjltt.moshi.adapters.FallbackOnNull;
import com.serjltt.moshi.adapters.Wrapped;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class ScrobbledSongModel {

    public static JsonAdapter<ScrobbledSongModel> jsonAdapter(Moshi moshi) {
        return new AutoValue_ScrobbledSongModel.MoshiJsonAdapter(moshi);
    }

    @Json(name = "track")
    @Wrapped(path = "#text")
    public abstract String getTrackName();

    @Json(name = "artist")
    @Wrapped(path = "#text")
    public abstract String getArtist();

    @Json(name = "album")
    @Wrapped(path = "#text")
    public abstract String getAlbum();

    @Json(name = "albumArtist")
    @Wrapped(path = "#text")
    public abstract String getAlbumArtist();

    @Json(name = "timestamp")
    @FallbackOnNull(fallbackLong = 0)
    public abstract long getTimeStamp();

    @Json(name = "ignoredMessage")
    @Wrapped(path = "code")
    public abstract int getIgnoredMessage();
}
