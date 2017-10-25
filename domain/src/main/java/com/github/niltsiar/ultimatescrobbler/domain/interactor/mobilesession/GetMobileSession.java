package com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.SingleUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.inject.Inject;

public class GetMobileSession extends SingleUseCase<String, Credentials> {

    private ScrobblerRepository scrobblerRepository;

    @Inject
    public GetMobileSession(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Single<String> buildUseCaseObservable(Credentials param) {
        return scrobblerRepository.getMobileSession(param);
    }
}
