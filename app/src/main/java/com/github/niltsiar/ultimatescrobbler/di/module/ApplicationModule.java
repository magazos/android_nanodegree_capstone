package com.github.niltsiar.ultimatescrobbler.di.module;

import android.app.Application;
import android.content.Context;
import com.github.niltsiar.ultimatescrobbler.BuildConfig;
import com.github.niltsiar.ultimatescrobbler.data.ScrobblerDataRepository;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerDataStore;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.data.source.ScrobblerRemoteDataStore;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession.GetMobileSession;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import com.github.niltsiar.ultimatescrobbler.remote.ApiKey;
import com.github.niltsiar.ultimatescrobbler.remote.ApiSecret;
import com.github.niltsiar.ultimatescrobbler.remote.ScrobblerRemoteImpl;
import com.github.niltsiar.ultimatescrobbler.remote.ScrobblerService;
import com.serjltt.moshi.adapters.Wrapped;
import com.squareup.moshi.Moshi;
import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class ApplicationModule {

    @Provides
    static Context provideContext(Application application) {
        return application;
    }

    @ApiKey
    @Provides
    static String provideApiKey() {
        return BuildConfig.LAST_FM_API_KEY;
    }

    @ApiSecret
    @Provides
    static String provideApiSecret() {
        return BuildConfig.LAST_FM_API_SECRET;
    }

    @Provides
    static ScrobblerRepository provideScrobblerRepository(ScrobblerDataRepository repository) {
        return repository;
    }

    @Provides
    static ScrobblerDataStore providesScrobblerDataStore(ScrobblerRemoteDataStore dataStore) {
        return dataStore;
    }

    @Provides
    static ScrobblerRemote provideScrobblerRemote(ScrobblerRemoteImpl remote) {
        return remote;
    }

    @Provides
    static ScrobblerService provideScrobblerService(OkHttpClient okHttpClient) {
        Moshi moshi = new Moshi.Builder().add(Wrapped.ADAPTER_FACTORY)
                                         .build();

        return new Retrofit.Builder().baseUrl(ScrobblerService.WS_URL)
                                     .client(okHttpClient)
                                     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                     .addConverterFactory(MoshiConverterFactory.create(moshi))
                                     .build()
                                     .create(ScrobblerService.class);
    }

    @Provides
    static GetMobileSession providesGetMobileSession(ScrobblerRepository repository) {
        return new GetMobileSession(repository, Schedulers.io(), AndroidSchedulers.mainThread());
    }
}
