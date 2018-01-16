package com.github.niltsiar.ultimatescrobbler.domain.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Credentials {

    public abstract String getUsername();

    public abstract String getPassword();

    public static Builder builder() {
        return new AutoValue_Credentials.Builder();
    }

    abstract Builder toBuilder();

    public Credentials withUsername(String newUsername) {
        return toBuilder().setUsername(newUsername)
                          .build();
    }

    public Credentials withPassword(String newPassword) {
        return toBuilder().setPassword(newPassword)
                          .build();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setUsername(String newUsername);

        public abstract Builder setPassword(String newPassword);

        public abstract Credentials build();
    }
}
