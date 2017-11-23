package com.github.niltsiar.ultimatescrobbler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.niltsiar.ultimatescrobbler.BuildConfig;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession.RequestMobileSessionTokenUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import dagger.android.AndroidInjection;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    RequestMobileSessionTokenUseCase requestMobileSessionTokenUseCase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @OnClick(R.id.test_button)
    public void onTestButtonClicked() {
        requestMobileSessionTokenUseCase.execute(Credentials.builder()
                                                            .setUsername(BuildConfig.TEST_USERNAME)
                                                            .setPassword(BuildConfig.TEST_PASSWORD)
                                                            .build())

                                        .subscribe(s -> Timber.i(s));
    }
}
