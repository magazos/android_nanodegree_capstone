package com.github.niltsiar.ultimatescrobbler.ui.configuration;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ConfigurationViewState {

    public abstract String getUsername();

    public abstract String getPassword();

    public abstract Integer getNumberOfSongsPerBatch();

    public abstract Boolean getSendNowPlaying();

    public static Builder builder() {
        return new AutoValue_ConfigurationViewState.Builder();
    }

    abstract Builder toBuilder();

    public ConfigurationViewState withUsername(String newUsername) {
        return toBuilder().setUsername(newUsername)
                          .build();
    }

    public ConfigurationViewState withPassword(String newPassword) {
        return toBuilder().setPassword(newPassword)
                          .build();
    }

    public ConfigurationViewState withNumberOfSongsPerBatch(Integer newNumberOfSongsPerBatch) {
        return toBuilder().setNumberOfSongsPerBatch(newNumberOfSongsPerBatch)
                          .build();
    }

    public ConfigurationViewState withSendNowPlating(Boolean newSendNowPlaying) {
        return toBuilder().setSendNowPlaying(newSendNowPlaying)
                          .build();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setUsername(String newUsername);

        public abstract Builder setPassword(String newPassword);

        public abstract Builder setNumberOfSongsPerBatch(Integer newNumberOfSongsPerBatch);

        public abstract Builder setSendNowPlaying(Boolean newSendNowPlaying);

        public abstract ConfigurationViewState build();
    }
}
