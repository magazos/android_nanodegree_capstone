package com.github.niltsiar.ultimatescrobbler.utils;

public class Utils {

    private static String SUFFIX_TITLE_TRANSITION_NAME = "title";
    private static String SUFFIX_ARTIST_TRANSTION_NAME = "artist";
    private static String SUFFIX_ALBUM_ART_TRANSITION_NAME = "album_art";

    private Utils() {
        //Avoid instances
    }

    public static String getTransitionNameForSongTitle(String baseName) {
        return baseName + SUFFIX_TITLE_TRANSITION_NAME;
    }

    public static String getTransitionNameForSongArtist(String baseName) {
        return baseName + SUFFIX_ARTIST_TRANSTION_NAME;
    }

    public static String getTransitionNameForSongAlbumArt(String baseName) {
        return baseName + SUFFIX_ALBUM_ART_TRANSITION_NAME;
    }
}
