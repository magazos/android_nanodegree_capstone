package com.github.niltsiar.ultimatescrobbler.remote.mapper;

public interface EntityMapper<M, E> {

    E mapFromRempte(M type);
}
