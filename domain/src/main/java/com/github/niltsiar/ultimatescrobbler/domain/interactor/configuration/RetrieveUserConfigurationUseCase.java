package com.github.niltsiar.ultimatescrobbler.domain.interactor.configuration;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.SingleUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.UserConfiguration;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ConfigurationRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.annotation.Nullable;

public class RetrieveUserConfigurationUseCase extends SingleUseCase<UserConfiguration, Void> {

    private ConfigurationRepository configurationRepository;

    public RetrieveUserConfigurationUseCase(ConfigurationRepository configurationRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.configurationRepository = configurationRepository;
    }

    @Override
    protected Single<UserConfiguration> buildUseCaseObservable(@Nullable Void param) {
        return configurationRepository.getUserConfiguration();
    }
}
