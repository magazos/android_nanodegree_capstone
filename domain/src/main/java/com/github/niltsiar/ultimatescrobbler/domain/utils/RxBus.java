package com.github.niltsiar.ultimatescrobbler.domain.utils;

import com.jakewharton.rxrelay2.PublishRelay;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class RxBus {

    private RxBus() {
        //No instances allowed
    }

    private static PublishRelay<Object> relay = PublishRelay.create();

    public static void send(Object event) {
        relay.accept(event);
    }

    public static Flowable<Object> getFlowable(BackpressureStrategy backpressureStrategy) {
        return relay.toFlowable(backpressureStrategy);
    }
}
