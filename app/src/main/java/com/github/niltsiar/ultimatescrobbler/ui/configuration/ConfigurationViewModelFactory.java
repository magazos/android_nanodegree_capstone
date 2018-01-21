package com.github.niltsiar.ultimatescrobbler.ui.configuration;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.configuration.RetrieveUserConfigurationUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.configuration.SaveUserConfigurationUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession.RequestMobileSessionTokenUseCase;
import javax.inject.Inject;

public class ConfigurationViewModelFactory implements ViewModelProvider.Factory {

    private RetrieveUserConfigurationUseCase retrieveUserConfigurationUseCase;
    private SaveUserConfigurationUseCase saveUserConfigurationUseCase;
    private RequestMobileSessionTokenUseCase requestMobileSessionTokenUseCase;

    @Inject
    public ConfigurationViewModelFactory(RetrieveUserConfigurationUseCase retrieveUserConfigurationUseCase,
            SaveUserConfigurationUseCase saveUserConfigurationUseCase,
            RequestMobileSessionTokenUseCase requestMobileSessionTokenUseCase) {
        this.retrieveUserConfigurationUseCase = retrieveUserConfigurationUseCase;
        this.saveUserConfigurationUseCase = saveUserConfigurationUseCase;
        this.requestMobileSessionTokenUseCase = requestMobileSessionTokenUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public ConfigurationViewModel create(@NonNull Class modelClass) {
        return new ConfigurationViewModel(retrieveUserConfigurationUseCase, saveUserConfigurationUseCase, requestMobileSessionTokenUseCase);
    }
}
