package com.github.niltsiar.ultimatescrobbler.data.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class CredentialsEntity {

    public abstract String getUsername();

    public abstract String getPassword();

    public static Builder builder() {
        return new AutoValue_CredentialsEntity.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setUsername(String newUsername);

        public abstract Builder setPassword(String newPassword);

        public abstract CredentialsEntity build();
    }
}
