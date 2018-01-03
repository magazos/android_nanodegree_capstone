package com.github.niltsiar.ultimatescrobbler.ui.configuration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession.RequestMobileSessionTokenUseCase;
import dagger.android.AndroidInjection;
import javax.inject.Inject;

public class ConfigurationActivity extends AppCompatActivity {

    @Inject
    RequestMobileSessionTokenUseCase requestMobileSessionTokenUseCase;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static Intent createCallingIntent(@NonNull Context context) {
        return new Intent(context, ConfigurationActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
