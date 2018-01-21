package com.github.niltsiar.ultimatescrobbler.data.repository;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public interface ConfigurationCache {

    Single<String> getMobileSessionToken();

    Consumer<? super String> saveMobileSessionToken();

    void removeMobileSessionToken();

    Single<String> getUsername();

    Consumer<? super String> saveUsername();

    void removeUsername();

    Single<String> getPassword();

    Consumer<? super String> savePassword();

    void removePassword();

    Single<Integer> getNumberOfSongsPerBatch();

    Consumer<? super Integer> saveNumberOfSongsPerBatch();

    void removeNumberOfSongsPerBatch();

    Single<Boolean> getSendNowPlaying();

    Consumer<? super Boolean> saveSendNowPlaying();

    void removeSendNowPlaying();
}
