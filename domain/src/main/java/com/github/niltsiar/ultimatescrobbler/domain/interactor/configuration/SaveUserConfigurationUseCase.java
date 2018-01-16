package com.github.niltsiar.ultimatescrobbler.domain.interactor.configuration;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.CompletableUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.UserConfiguration;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ConfigurationRepository;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.annotation.Nonnull;

public class SaveUserConfigurationUseCase extends CompletableUseCase<UserConfiguration> {

    private ConfigurationRepository configurationRepository;

    public SaveUserConfigurationUseCase(ConfigurationRepository configurationRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.configurationRepository = configurationRepository;
    }

    @Override
    protected Completable buildUseCaseObservable(@Nonnull UserConfiguration userConfiguration) {
        return configurationRepository.saveUserConfiguration(userConfiguration);
    }
}
