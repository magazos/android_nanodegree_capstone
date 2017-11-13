package com.github.niltsiar.ultimatescrobbler.domain.interactor;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import javax.annotation.Nullable;

public abstract class ObservableUseCase<T, V> {

    private Scheduler executionScheduler;
    private Scheduler postExecutionScheduler;

    public ObservableUseCase(Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        this.executionScheduler = executionScheduler;
        this.postExecutionScheduler = postExecutionScheduler;
    }

    protected abstract Observable<T> buildUseCaseObservable(@Nullable V param);

    public Observable<T> execute(@Nullable V param) {
        Observable<T> single = buildUseCaseObservable(param);

        if (null != executionScheduler) {
            single = single.subscribeOn(executionScheduler);
        }

        if (null != postExecutionScheduler) {
            single = single.observeOn(postExecutionScheduler);
        }

        return single;
    }
}
