package com.github.niltsiar.ultimatescrobbler.ui.configuration;

import android.arch.lifecycle.ViewModel;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.configuration.RetrieveUserConfigurationUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.configuration.SaveUserConfigurationUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession.RequestMobileSessionTokenUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.UserConfiguration;
import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ConfigurationViewModel extends ViewModel {

    private RetrieveUserConfigurationUseCase retrieveUserConfigurationUseCase;
    private SaveUserConfigurationUseCase saveUserConfigurationUseCase;
    private RequestMobileSessionTokenUseCase requestMobileSessionTokenUseCase;
    private BehaviorRelay<ConfigurationViewState> configurationViewStateBehaviorRelay;
    private CompositeDisposable disposables;
    private ConfigurationViewState configurationViewState;
    private UserConfiguration userConfiguration;

    public ConfigurationViewModel(RetrieveUserConfigurationUseCase retrieveUserConfigurationUseCase,
            SaveUserConfigurationUseCase saveUserConfigurationUseCase,
            RequestMobileSessionTokenUseCase requestMobileSessionTokenUseCase) {
        this.retrieveUserConfigurationUseCase = retrieveUserConfigurationUseCase;
        this.saveUserConfigurationUseCase = saveUserConfigurationUseCase;
        this.requestMobileSessionTokenUseCase = requestMobileSessionTokenUseCase;
        configurationViewStateBehaviorRelay = BehaviorRelay.create();
        disposables = new CompositeDisposable();

        Disposable disposable = retrieveUserConfigurationUseCase.execute(null)
                                                                .subscribe(userConfiguration -> {
                                                                    this.userConfiguration = userConfiguration;
                                                                    configurationViewState = ConfigurationViewStateMapper.mapFromUserConfiguration(userConfiguration);
                                                                    configurationViewStateBehaviorRelay.accept(configurationViewState);
                                                                });
        disposables.add(disposable);
    }

    public Observable<ConfigurationViewState> getConfigurationViewState() {
        return configurationViewStateBehaviorRelay;
    }

    public void setUsername(String username) {
        UserConfiguration newUserConfiguration = userConfiguration.withUserCredentials(userConfiguration.getUserCredentials()
                                                                                                        .withUsername(username));
        updateUserConfiguration(newUserConfiguration);
    }

    public void setPassword(String password) {
        UserConfiguration newUserConfiguration = userConfiguration.withUserCredentials(userConfiguration.getUserCredentials()
                                                                                                        .withPassword(password));
        updateUserConfiguration(newUserConfiguration);
    }

    public void setNumberOfSongsPerBatch(int numberOfSongsPerBatch) {
        UserConfiguration newUserConfiguration = userConfiguration.withNumberOfSongsPerBatch(numberOfSongsPerBatch);
        updateUserConfiguration(newUserConfiguration);
    }

    public void setSendNowPlaying(boolean sendNowPlaying) {
        UserConfiguration newUserConfiguration = userConfiguration.withSendNowPlaying(sendNowPlaying);
        updateUserConfiguration(newUserConfiguration);
    }

    public void testUserCredentials() {
        requestMobileSessionTokenUseCase.execute(userConfiguration.getUserCredentials())
                                        .subscribe();
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }

    private void updateUserConfiguration(UserConfiguration newUserConfiguration) {
        saveUserConfigurationUseCase.execute(newUserConfiguration)
                                    .subscribe(() -> userConfiguration = newUserConfiguration);
    }
}
