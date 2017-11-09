package com.github.niltsiar.ultimatescrobbler.remote.model;

import com.google.auto.value.AutoValue;
import com.serjltt.moshi.adapters.Wrapped;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.util.List;

@AutoValue
public abstract class InfoSongModel {

    public static JsonAdapter<InfoSongModel> jsonAdapter(Moshi moshi) {
        return new AutoValue_InfoSongModel.MoshiJsonAdapter(moshi);
    }

    @Json(name = "name")
    public abstract String getTrackName();

    @Json(name = "artist")
    @Wrapped(path = "name")
    public abstract String getArtist();

    @Json(name = "album")
    @Wrapped(path = "title")
    public abstract String getAlbum();

    @Json(name = "album")
    @Wrapped(path = "artist")
    public abstract String getAlbumArtist();

    @Json(name = "album")
    @Wrapped(path = "image")
    public abstract String getAlbumArt();

    @Json(name = "toptags")
    @Wrapped(path = {"tag", "name"})
    public abstract List<String> getTags();

    @Json(name = "wiki")
    @Wrapped(path = "summary")
    public abstract String getSummary();

    @Json(name = "wiki")
    @Wrapped(path = "content")
    public abstract String getContent();
}
