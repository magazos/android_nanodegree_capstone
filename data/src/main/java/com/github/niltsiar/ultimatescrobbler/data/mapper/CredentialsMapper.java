package com.github.niltsiar.ultimatescrobbler.data.mapper;

import com.github.niltsiar.ultimatescrobbler.data.model.CredentialsEntity;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import javax.inject.Inject;

public class CredentialsMapper implements Mapper<CredentialsEntity, Credentials> {

    @Inject
    public CredentialsMapper() {
    }

    @Override
    public Credentials mapFromEntity(CredentialsEntity type) {
        return Credentials.builder()
                          .setUsername(type.getUsername())
                          .setPassword(type.getPassword())
                          .build();
    }

    @Override
    public CredentialsEntity mapToEntity(Credentials type) {
        return CredentialsEntity.builder()
                                .setUsername(type.getUsername())
                                .setPassword(type.getPassword())
                                .build();
    }
}
