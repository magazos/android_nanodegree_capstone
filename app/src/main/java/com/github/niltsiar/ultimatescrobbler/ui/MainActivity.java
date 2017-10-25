package com.github.niltsiar.ultimatescrobbler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.niltsiar.ultimatescrobbler.BuildConfig;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import dagger.android.AndroidInjection;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    ScrobblerRepository scrobblerRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.test_button)
    public void onTestButtonClicked() {
        scrobblerRepository.getMobileSession(Credentials.builder()
                                                        .setUsername(BuildConfig.TEST_USERNAME)
                                                        .setPassword(BuildConfig.TEST_PASSWORD)
                                                        .build())
                           .subscribeWith(new DisposableSingleObserver<String>() {
                               @Override
                               public void onSuccess(String s) {
                                   Timber.i(s);
                               }

                               @Override
                               public void onError(Throwable e) {

                               }
                           });
    }
}
