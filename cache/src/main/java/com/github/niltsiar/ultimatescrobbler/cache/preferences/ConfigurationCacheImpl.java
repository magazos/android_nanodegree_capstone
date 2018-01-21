package com.github.niltsiar.ultimatescrobbler.cache.preferences;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.github.niltsiar.ultimatescrobbler.data.repository.ConfigurationCache;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

public class ConfigurationCacheImpl implements ConfigurationCache {

    private Preference<String> mobileSessionPreference;
    private Preference<String> usernamePreference;
    private Preference<String> passwordPreference;
    private Preference<Integer> numberOfSongsPerBatchPreference;
    private Preference<Boolean> sendNowPlayingPreference;

    private static final String MOBILE_SESSION_PREFERENCE = "MOBILE_SESSION_PREFERENCE";
    private static final String USERNAME_PREFERENCE = "USERNAME_PREFERENCE";
    private static final String PASSWORD_PREFERENCE = "PASSWORD_PREFERENCE";
    private static final String NUMBER_OF_SONGS_PER_BATCH_PREFERENCE = "NUMBER_OF_SONGS_PER_BATCH_PREFERENCE";
    private static final String SEND_NOW_PLAYING_PREFERENCE = "SEND_NOW_PLAYING_PREFERENCE";

    private static final int NUMBER_OF_SONGS_PER_BATCH_DEFAULT_VALUE = 10;
    private static final boolean SEND_NOW_PLAYING_DEFAULT_VALUE = false;
    private static final String EMPTY_STRING = "";

    @Inject
    public ConfigurationCacheImpl(RxSharedPreferences preferences) {
        mobileSessionPreference = preferences.getString(MOBILE_SESSION_PREFERENCE, EMPTY_STRING);
        usernamePreference = preferences.getString(USERNAME_PREFERENCE, EMPTY_STRING);
        passwordPreference = preferences.getString(PASSWORD_PREFERENCE, EMPTY_STRING);
        numberOfSongsPerBatchPreference = preferences.getInteger(NUMBER_OF_SONGS_PER_BATCH_PREFERENCE, NUMBER_OF_SONGS_PER_BATCH_DEFAULT_VALUE);
        sendNowPlayingPreference = preferences.getBoolean(SEND_NOW_PLAYING_PREFERENCE, SEND_NOW_PLAYING_DEFAULT_VALUE);
    }

    @Override
    public Single<String> getMobileSessionToken() {
        return mobileSessionPreference.asObservable()
                                      .firstOrError();
    }

    @Override
    public Consumer<? super String> saveMobileSessionToken() {
        return mobileSessionPreference.asConsumer();
    }

    @Override
    public void removeMobileSessionToken() {
        mobileSessionPreference.delete();
    }

    @Override
    public Single<String> getUsername() {
        return usernamePreference.asObservable()
                                 .firstOrError();
    }

    @Override
    public Consumer<? super String> saveUsername() {
        return usernamePreference.asConsumer();
    }

    @Override
    public void removeUsername() {
        usernamePreference.delete();
    }

    @Override
    public Single<String> getPassword() {
        return passwordPreference.asObservable()
                                 .firstOrError();
    }

    @Override
    public Consumer<? super String> savePassword() {
        return passwordPreference.asConsumer();
    }

    @Override
    public void removePassword() {
        passwordPreference.delete();
    }

    @Override
    public Single<Integer> getNumberOfSongsPerBatch() {
        return numberOfSongsPerBatchPreference.asObservable()
                                              .firstOrError();
    }

    @Override
    public Consumer<? super Integer> saveNumberOfSongsPerBatch() {
        return numberOfSongsPerBatchPreference.asConsumer();
    }

    @Override
    public void removeNumberOfSongsPerBatch() {
        numberOfSongsPerBatchPreference.delete();
    }

    @Override
    public Single<Boolean> getSendNowPlaying() {
        return sendNowPlayingPreference.asObservable()
                                       .firstOrError();
    }

    @Override
    public Consumer<? super Boolean> saveSendNowPlaying() {
        return sendNowPlayingPreference.asConsumer();
    }

    @Override
    public void removeSendNowPlaying() {
        sendNowPlayingPreference.delete();
    }
}
