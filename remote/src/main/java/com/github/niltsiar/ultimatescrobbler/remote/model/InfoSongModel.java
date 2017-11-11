package com.github.niltsiar.ultimatescrobbler.remote.model;

import android.text.TextUtils;
import com.google.auto.value.AutoValue;
import com.google.auto.value.extension.memoized.Memoized;
import com.serjltt.moshi.adapters.Wrapped;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import io.reactivex.Observable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

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
    @Nullable
    abstract InfoAlbumModel getInfoAlbum();

    @Nullable
    public String getAlbum() {
        return null != getInfoAlbum() ? getInfoAlbum().getTitle() : null;
    }

    @Nullable
    public String getAlbumArtist() {
        return null != getInfoAlbum() ? getInfoAlbum().getArtist() : null;
    }

    @Memoized
    @Nullable
    public String getAlbumArt() {
        if (null == getInfoAlbum()) {
            return null;
        }

        Map<String, String> imagesMap = Observable.fromIterable(getInfoAlbum().getImages())
                                                  .toMap(InfoImageModel::getSize, InfoImageModel::getUrl)
                                                  .blockingGet();

        return getBestAlbumArtImage(imagesMap);
    }

    @Json(name = "toptags")
    @Wrapped(path = "tag")
    abstract List<InfoTagModel> getInfoTags();

    @Memoized
    public List<String> getTags() {
        return Observable.fromIterable(getInfoTags())
                         .map(InfoTagModel::getName)
                         .toList()
                         .blockingGet();
    }

    @Json(name = "wiki")
    @Nullable
    abstract InfoWikiModel getInfoWiki();

    @Nullable
    public String getContent() {
        return null != getInfoWiki() ? getInfoWiki().getContent() : null;
    }

    private String getBestAlbumArtImage(Map<String, String> imagesMap) {
        return checkForImage(imagesMap, new String[]{"extralarge", "large", "medium", "small"});
    }

    private String checkForImage(Map<String, String> imagesMap, String[] keys) {
        String key = keys[0];
        if (1 == keys.length) {
            return checkForImage(imagesMap, key);
        } else {
            String url = checkForImage(imagesMap, key);
            if (!TextUtils.isEmpty(url)) {
                return url;
            } else {
                return checkForImage(imagesMap, Arrays.copyOfRange(keys, 1, keys.length));
            }
        }
    }

    private String checkForImage(Map<String, String> imagesMap, String key) {
        if (imagesMap.containsKey(key)) {
            return imagesMap.get(key);
        } else {
            return "";
        }
    }

    @AutoValue
    abstract static class InfoAlbumModel {

        public static JsonAdapter<InfoAlbumModel> jsonAdapter(Moshi moshi) {
            return new AutoValue_InfoSongModel_InfoAlbumModel.MoshiJsonAdapter(moshi);
        }

        @Json(name = "title")
        abstract String getTitle();

        @Json(name = "artist")
        abstract String getArtist();

        @Json(name = "image")
        abstract List<InfoImageModel> getImages();
    }

    @AutoValue
    abstract static class InfoImageModel {

        public static JsonAdapter<InfoImageModel> jsonAdapter(Moshi moshi) {
            return new AutoValue_InfoSongModel_InfoImageModel.MoshiJsonAdapter(moshi);
        }

        @Json(name = "#text")
        abstract String getUrl();

        @Json(name = "size")
        abstract String getSize();
    }

    @AutoValue
    abstract static class InfoTagModel {

        public static JsonAdapter<InfoTagModel> jsonAdapter(Moshi moshi) {
            return new AutoValue_InfoSongModel_InfoTagModel.MoshiJsonAdapter(moshi);
        }

        @Json(name = "name")
        abstract String getName();
    }

    @AutoValue
    abstract static class InfoWikiModel {
        public static JsonAdapter<InfoWikiModel> jsonAdapter(Moshi moshi) {
            return new AutoValue_InfoSongModel_InfoWikiModel.MoshiJsonAdapter(moshi);
        }

        @Json(name = "summary")
        abstract String getSummary();

        @Json(name = "content")
        abstract String getContent();
    }
}
