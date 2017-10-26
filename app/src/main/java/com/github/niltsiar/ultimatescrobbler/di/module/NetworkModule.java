package com.github.niltsiar.ultimatescrobbler.di.module;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {



    @Provides
    static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }
}
