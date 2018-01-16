package com.github.niltsiar.ultimatescrobbler.domain.repository;

import com.github.niltsiar.ultimatescrobbler.domain.model.UserConfiguration;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface ConfigurationRepository {

    Single<UserConfiguration> getUserConfiguration();

    Completable saveUserConfiguration(UserConfiguration userConfiguration);
}
