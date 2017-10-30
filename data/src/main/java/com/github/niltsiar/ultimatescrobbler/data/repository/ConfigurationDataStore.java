package com.github.niltsiar.ultimatescrobbler.data.repository;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public interface ConfigurationDataStore {

    Single<String> getMobileSession();

    Consumer<? super String> saveMobileSession();

    void removeMobileSession();
}
