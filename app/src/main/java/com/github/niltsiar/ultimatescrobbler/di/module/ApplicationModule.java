package com.github.niltsiar.ultimatescrobbler.di.module;

import android.app.Application;
import android.content.Context;
import com.github.niltsiar.ultimatescrobbler.data.ScrobblerDataRepository;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import com.github.niltsiar.ultimatescrobbler.remote.ScrobblerRemoteImpl;
import com.github.niltsiar.ultimatescrobbler.remote.ScrobblerService;
import com.serjltt.moshi.adapters.Wrapped;
import com.squareup.moshi.Moshi;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

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

    @Provides
    static ScrobblerService provideScrobblerService(OkHttpClient okHttpClient) {
        Moshi moshi = new Moshi.Builder().add(Wrapped.ADAPTER_FACTORY)
                                         .build();

        return new Retrofit.Builder().baseUrl(ScrobblerService.WS_URL)
                                     .client(okHttpClient)
                                     .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                     .addConverterFactory(MoshiConverterFactory.create(moshi))
                                     .build()
                                     .create(ScrobblerService.class);
    }

    @Provides
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }
}
