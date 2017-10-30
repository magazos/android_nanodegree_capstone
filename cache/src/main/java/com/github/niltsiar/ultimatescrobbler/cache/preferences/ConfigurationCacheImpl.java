package com.github.niltsiar.ultimatescrobbler.cache.preferences;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.github.niltsiar.ultimatescrobbler.data.repository.ConfigurationCache;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;

public class ConfigurationCacheImpl implements ConfigurationCache {

    RxSharedPreferences preferences;
    Preference<String> mobileSessionPreference;

    private static final String MOBILE_SESSION_PREFERENCE = "MOBILE_SESSION_PREFERENCE";

    @Inject
    public ConfigurationCacheImpl(RxSharedPreferences preferences) {
        this.preferences = preferences;
        mobileSessionPreference = this.preferences.getString(MOBILE_SESSION_PREFERENCE);
    }

    @Override
    public Single<String> getMobileSessionToken() {
        return mobileSessionPreference.asObservable()
                                      .firstElement()
                                      .toSingle();
    }

    @Override
    public Consumer<? super String> saveMobileSessionToken() {
        return mobileSessionPreference.asConsumer();
    }

    @Override
    public void removeMobileSessionToken() {
        mobileSessionPreference.delete();
    }
}
