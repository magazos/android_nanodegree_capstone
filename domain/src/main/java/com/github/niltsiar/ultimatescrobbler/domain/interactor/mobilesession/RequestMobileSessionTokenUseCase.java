package com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.SingleUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.annotation.Nonnull;

public class RequestMobileSessionTokenUseCase extends SingleUseCase<String, Credentials> {

    private ScrobblerRepository scrobblerRepository;

    public RequestMobileSessionTokenUseCase(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Single<String> buildUseCaseObservable(@Nonnull Credentials param) {
        return scrobblerRepository.requestMobileSessionToken(param);
    }
}
