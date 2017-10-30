package com.github.niltsiar.ultimatescrobbler.data.repository;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public interface ConfigurationCache {

    Single<String> getMobileSessionToken();

    Consumer<? super String> saveMobileSessionToken();

    void removeMobileSessionToken();
}
