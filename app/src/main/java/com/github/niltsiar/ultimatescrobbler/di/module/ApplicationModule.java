package com.github.niltsiar.ultimatescrobbler.di.module;

import android.app.Application;
import android.content.Context;
import com.github.niltsiar.ultimatescrobbler.data.ScrobblerDataRepository;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import com.github.niltsiar.ultimatescrobbler.remote.ScrobblerRemoteImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    @Provides
    static Context provideContext(Application application) {
        return application;
    }

    @Provides
    static ScrobblerRepository provideScrobblerRepository(ScrobblerDataRepository repository) {
        return repository;
    }

    @Provides
    static ScrobblerRemote provideScrobblerRemote(ScrobblerRemoteImpl scrobblerRemote) {
        return scrobblerRemote;
    }
}
