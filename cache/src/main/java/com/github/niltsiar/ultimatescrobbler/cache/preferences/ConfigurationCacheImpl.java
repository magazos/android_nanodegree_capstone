package com.github.niltsiar.ultimatescrobbler.cache.preferences;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.github.niltsiar.ultimatescrobbler.data.repository.ConfigurationDataStore;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

public class ConfigurationCacheImpl implements ConfigurationDataStore {

    RxSharedPreferences preferences;
    Preference<String> mobileSessionPreference;

    private static final String MOBILE_SESSION_PREFERENCE = "MOBILE_SESSION_PREFERENCE";

    @Inject
    public ConfigurationCacheImpl(RxSharedPreferences preferences) {
        this.preferences = preferences;
        mobileSessionPreference = this.preferences.getString(MOBILE_SESSION_PREFERENCE);
    }

    @Override
    public Single<String> getMobileSession() {
        return mobileSessionPreference.asObservable()
                                      .firstElement()
                                      .toSingle();
    }

    @Override
    public Consumer<? super String> saveMobileSession() {
        return mobileSessionPreference.asConsumer();
    }

    @Override
    public void removeMobileSession() {
        mobileSessionPreference.delete();
    }
}
