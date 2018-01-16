package com.github.niltsiar.ultimatescrobbler.domain.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class UserConfiguration {

    public abstract Credentials getUserCredentials();

    public abstract Integer getNumberOfSongsPerBatch();

    public abstract Boolean getSendNowPlaying();

    public static Builder builder() {
        return new AutoValue_UserConfiguration.Builder();
    }

    abstract Builder toBuilder();

    public UserConfiguration withUserCredentials(Credentials newUserCredentials) {
        return toBuilder().setUserCredentials(newUserCredentials)
                          .build();
    }

    public UserConfiguration withNumberOfSongsPerBatch(Integer newNumberOfSongsPerBatch) {
        return toBuilder().setNumberOfSongsPerBatch(newNumberOfSongsPerBatch)
                          .build();
    }

    public UserConfiguration withSendNowPlaying(Boolean newSendNowPlaying) {
        return toBuilder().setSendNowPlaying(newSendNowPlaying)
                          .build();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setUserCredentials(Credentials newUserCredentials);

        public abstract Builder setNumberOfSongsPerBatch(Integer newNumberOfSongsPerBatch);

        public abstract Builder setSendNowPlaying(Boolean newSendNowPlaying);

        public abstract UserConfiguration build();
    }
}
