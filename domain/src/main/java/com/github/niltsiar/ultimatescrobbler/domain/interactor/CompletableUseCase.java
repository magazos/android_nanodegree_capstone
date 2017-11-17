package com.github.niltsiar.ultimatescrobbler.domain.interactor;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.annotation.Nullable;

public abstract class CompletableUseCase<T> {

    private Scheduler executionScheduler;
    private Scheduler postExecutionScheduler;

    public CompletableUseCase(Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        this.executionScheduler = executionScheduler;
        this.postExecutionScheduler = postExecutionScheduler;
    }

    protected abstract Completable buildUseCaseObservable(@Nullable T param);

    public Completable execute(@Nullable T param) {
        Completable completable = buildUseCaseObservable(param);

        if (null != executionScheduler) {
            completable = completable.subscribeOn(executionScheduler);
        }

        if (null != postExecutionScheduler) {
            completable = completable.observeOn(postExecutionScheduler);
        }

        return completable;
    }
}
