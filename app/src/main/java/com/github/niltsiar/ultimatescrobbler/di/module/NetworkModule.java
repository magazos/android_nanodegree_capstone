package com.github.niltsiar.ultimatescrobbler.di.module;

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
public class NetworkModule {

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
