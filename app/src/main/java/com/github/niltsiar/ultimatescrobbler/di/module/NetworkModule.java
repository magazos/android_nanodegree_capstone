package com.github.niltsiar.ultimatescrobbler.di.module;

import com.github.niltsiar.ultimatescrobbler.remote.ScrobblerService;
import dagger.Module;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class NetworkModule {

    static ScrobblerService provideScrobblerService() {
        return new Retrofit.Builder().baseUrl()
                                     .client(new OkHttpClient())
                                     .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                     .build()

    }
}
