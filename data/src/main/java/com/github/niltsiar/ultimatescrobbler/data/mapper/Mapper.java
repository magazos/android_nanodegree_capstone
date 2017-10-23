package com.github.niltsiar.ultimatescrobbler.data.mapper;

public interface Mapper<E, D> {

    D mapFromEntity(E type);

    E mapToEntity(D type);
}
