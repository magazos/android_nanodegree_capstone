package com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession;

import com.github.niltsiar.ultimatescrobbler.domain.error.InvalidCredentialsError;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.SingleUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import com.pacoworks.rxsealedunions2.Union2;
import com.pacoworks.rxsealedunions2.generic.UnionFactories;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.annotation.Nonnull;

public class RequestMobileSessionTokenUseCase extends SingleUseCase<Union2<InvalidCredentialsError, String>, Credentials> {

    private ScrobblerRepository scrobblerRepository;

    public RequestMobileSessionTokenUseCase(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Single<Union2<InvalidCredentialsError, String>> buildUseCaseObservable(@Nonnull Credentials param) {
        return scrobblerRepository.requestMobileSessionToken(param)
                                  .map(token -> {
                                      Union2.Factory<InvalidCredentialsError, String> factory = UnionFactories.doubletFactory();
                                      return factory.second(token);
                                  })
                                  .onErrorReturn(throwable -> {
                                      Union2.Factory<InvalidCredentialsError, String> factory = UnionFactories.doubletFactory();
                                      return factory.first(new InvalidCredentialsError());
                                  });
    }
}
