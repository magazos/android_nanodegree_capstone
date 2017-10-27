package com.github.niltsiar.ultimatescrobbler.di.module;

import android.content.Context;
import com.github.simonpercic.oklog3.OkLogInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module
public class NetworkModule {

    private static String HTTP_LOG_INTERCEPTOR_TAG = "OkHttp";

    @Provides
    static ChuckInterceptor provideChuckInterceptor(Context context) {
        return new ChuckInterceptor(context);
    }

    @Provides
    static OkHttpClient provideOkHttpClient(ChuckInterceptor chuckInterceptor) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag(HTTP_LOG_INTERCEPTOR_TAG)
                                                                                                .d(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder()
                                                            .withAllLogData()
                                                            .shortenInfoUrl(false)
                                                            .build();

        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor)
                                         .addInterceptor(okLogInterceptor)
                                         .addInterceptor(chuckInterceptor)
                                         .build();
    }
}
