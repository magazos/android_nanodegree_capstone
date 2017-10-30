package com.github.niltsiar.ultimatescrobbler.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.github.niltsiar.ultimatescrobbler.BuildConfig;
import com.github.niltsiar.ultimatescrobbler.cache.preferences.ConfigurationCacheImpl;
import com.github.niltsiar.ultimatescrobbler.data.ScrobblerDataRepository;
import com.github.niltsiar.ultimatescrobbler.data.repository.ConfigurationCache;
import com.github.niltsiar.ultimatescrobbler.data.repository.ScrobblerRemote;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession.RequestMobileSessionToken;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.nowplaying.SendNowPlaying;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import com.github.niltsiar.ultimatescrobbler.remote.ScrobblerRemoteImpl;
import com.github.niltsiar.ultimatescrobbler.remote.ScrobblerService;
import com.github.niltsiar.ultimatescrobbler.remote.model.AutoValueMoshiAdapterFactory;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.ApiKey;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.ApiSecret;
import com.github.niltsiar.ultimatescrobbler.remote.qualifiers.MobileSessionToken;
import com.serjltt.moshi.adapters.FallbackOnNull;
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
        return application.getApplicationContext();
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

    @MobileSessionToken
    @Provides
    static String provideMobileSessionToken(ConfigurationCache cache) {
        return cache.getMobileSessionToken()
                    .blockingGet();
    }

    @Provides
    static ScrobblerRepository provideScrobblerRepository(ScrobblerDataRepository repository) {
        return repository;
    }

    @Provides
    static ScrobblerRemote provideScrobblerRemote(ScrobblerRemoteImpl remote) {
        return remote;
    }

    @Provides
    static ScrobblerService provideScrobblerService(OkHttpClient okHttpClient) {
        Moshi moshi = new Moshi.Builder().add(AutoValueMoshiAdapterFactory.create())
                                         .add(Wrapped.ADAPTER_FACTORY)
                                         .add(FallbackOnNull.ADAPTER_FACTORY)
                                         .build();

        return new Retrofit.Builder().baseUrl(ScrobblerService.WS_URL)
                                     .client(okHttpClient)
                                     .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                     .addConverterFactory(MoshiConverterFactory.create(moshi))
                                     .build()
                                     .create(ScrobblerService.class);
    }

    @Provides
    static RequestMobileSessionToken providesRequestMobileSessionToken(ScrobblerRepository repository) {
        return new RequestMobileSessionToken(repository, Schedulers.io(), AndroidSchedulers.mainThread());
    }

    @Provides
    static SendNowPlaying providesSendNowPlaying(ScrobblerRepository repository) {
        return new SendNowPlaying(repository, Schedulers.io(), AndroidSchedulers.mainThread());
    }

    @Provides
    static ConfigurationCache providesConfigurationDataStore(RxSharedPreferences rxSharedPreferences) {
        return new ConfigurationCacheImpl(rxSharedPreferences);
    }

    @Provides
    static RxSharedPreferences providesRxSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return RxSharedPreferences.create(sharedPreferences);
    }
}
