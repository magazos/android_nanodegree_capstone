package com.github.niltsiar.ultimatescrobbler.ui.configuration;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ConfigurationViewState {

    public abstract String getUsername();

    public abstract String getPassword();

    public abstract int getNumberOfSongsPerBatch();

    public abstract boolean isSendNowPlaying();

    public abstract boolean isInvalidCredentialsError();

    public static Builder builder() {
        return new AutoValue_ConfigurationViewState.Builder().setSendNowPlaying(false)
                                                             .setInvalidCredentialsError(false);
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

    public ConfigurationViewState withSendNowPlaying(Boolean newSendNowPlaying) {
        return toBuilder().setSendNowPlaying(newSendNowPlaying)
                          .build();
    }

    public ConfigurationViewState withInvalidCredentialsError(Boolean invalidCredentialsError) {
        return toBuilder().setInvalidCredentialsError(invalidCredentialsError)
                          .build();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setUsername(String newUsername);

        public abstract Builder setPassword(String newPassword);

        public abstract Builder setNumberOfSongsPerBatch(int newNumberOfSongsPerBatch);

        public abstract Builder setSendNowPlaying(boolean newSendNowPlaying);

        public abstract Builder setInvalidCredentialsError(boolean invalidCredentialsError);

        public abstract ConfigurationViewState build();
    }
}
