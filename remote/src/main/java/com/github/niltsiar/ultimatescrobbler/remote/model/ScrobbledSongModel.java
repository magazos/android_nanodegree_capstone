package com.github.niltsiar.ultimatescrobbler.remote.model;

import com.google.auto.value.AutoValue;
import com.serjltt.moshi.adapters.FallbackOnNull;
import com.squareup.moshi.Json;

@AutoValue
public abstract class ScrobbledSongModel {

    @Json(name = "track")
    public abstract String getTrackName();

    @Json(name = "artist")
    public abstract String getArtist();

    @Json(name = "album")
    public abstract String getAlbum();

    @Json(name = "albumArtist")
    public abstract String getAlbumArtist();

    @Json(name = "timestamp")
    @FallbackOnNull(fallbackInt = Integer.MIN_VALUE)
    public abstract int getTimeStamp();

    @Json(name = "ignoredMessage")
    public abstract int getIgnoredMessage();
}
