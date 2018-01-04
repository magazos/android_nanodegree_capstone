package com.github.niltsiar.ultimatescrobbler.ui.configuration;

import com.github.niltsiar.ultimatescrobbler.domain.model.UserConfiguration;

public class ConfigurationViewStateMapper {

    public static ConfigurationViewState mapFromUserConfiguration(UserConfiguration userConfiguration) {
        return ConfigurationViewState.builder()
                                     .setUsername(userConfiguration.getUserCredentials()
                                                                   .getUsername())
                                     .setPassword(userConfiguration.getUserCredentials()
                                                                   .getPassword())
                                     .setNumberOfSongsPerBatch(userConfiguration.getNumberOfSongsPerBatch())
                                     .setSendNowPlaying(userConfiguration.getSendNowPlaying())
                                     .build();
    }
}
