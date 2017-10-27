package com.github.niltsiar.ultimatescrobbler.di.module;

import com.github.simonpercic.oklog3.OkLogInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module
public class NetworkModule {

    private static String HTTP_LOG_INTERCEPTOR_TAG = "OkHttp";

    @Provides
    static OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Timber.tag(HTTP_LOG_INTERCEPTOR_TAG)
                      .d(message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder()
                                                            .withAllLogData()
                                                            .shortenInfoUrl(false)
                                                            .build();

        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                                         .addInterceptor(okLogInterceptor)
                                         .build();
    }
}
