package com.github.niltsiar.ultimatescrobbler.data;

import com.github.niltsiar.ultimatescrobbler.data.repository.ConfigurationCache;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.UserConfiguration;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ConfigurationRepository;
import io.reactivex.Completable;
import io.reactivex.Single;
import javax.inject.Inject;

public class UserConfigurationDataRepository implements ConfigurationRepository {

    private ConfigurationCache configurationCache;

    @Inject
    public UserConfigurationDataRepository(ConfigurationCache configurationCache) {
        this.configurationCache = configurationCache;
    }

    @Override
    public Single<UserConfiguration> getUserConfiguration() {
        return Single.zip(configurationCache.getUsername(), configurationCache.getPassword(), configurationCache.getNumberOfSongsPerBatch(), configurationCache.getSendNowPlaying(),
                          (username, password, numberOfSongsPerBatch, sendNowPlaying) -> {
                              Credentials userCredentials = Credentials.builder()
                                                                       .setUsername(username)
                                                                       .setPassword(password)
                                                                       .build();
                              return UserConfiguration.builder()
                                                      .setUserCredentials(userCredentials)
                                                      .setNumberOfSongsPerBatch(numberOfSongsPerBatch)
                                                      .setSendNowPlaying(sendNowPlaying)
                                                      .build();
                          });
    }

    @Override
    public Completable saveUserConfiguration(UserConfiguration userConfiguration) {
        return Single.fromCallable(() -> userConfiguration.getUserCredentials()
                                                          .getUsername())
                     .doOnSuccess(configurationCache.saveUsername())
                     .flatMap(ignored -> Single.fromCallable(() -> userConfiguration.getUserCredentials()
                                                                                    .getPassword())
                                               .doOnSuccess(configurationCache.savePassword()))
                     .flatMap(ignored -> Single.fromCallable(userConfiguration::getNumberOfSongsPerBatch)
                                               .doOnSuccess(configurationCache.saveNumberOfSongsPerBatch()))
                     .flatMap(ignored -> Single.fromCallable(userConfiguration::getSendNowPlaying)
                                               .doOnSuccess(configurationCache.saveSendNowPlaying()))
                     .toCompletable();
    }
}
