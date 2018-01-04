package com.github.niltsiar.ultimatescrobbler.ui.configuration;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.niltsiar.ultimatescrobbler.R;
import com.warkiz.widget.IndicatorSeekBar;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class ConfigurationActivity extends AppCompatActivity {

    @Inject
    ConfigurationViewModelFactory configurationViewModelFactory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.send_songs_batches_slider)
    IndicatorSeekBar songBatches;
    @BindView(R.id.lastfm_username)
    TextView lastfmUsername;
    @BindView(R.id.lastfm_password)
    TextView lastfmPassword;
    @BindView(R.id.send_now_playing)
    Switch nowPlaying;
    @BindView(R.id.lastfm_test_credentials)
    Button lastfmTestCredentials;

    private ConfigurationViewModel configurationViewModel;
    private CompositeDisposable disposables;

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

        configurationViewModel = ViewModelProviders.of(this, configurationViewModelFactory)
                                                   .get(ConfigurationViewModel.class);

        disposables = new CompositeDisposable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Disposable disposable = configurationViewModel.getConfigurationViewState()
                                                      .subscribe(viewState -> {
                                                          lastfmUsername.setText(viewState.getUsername());
                                                          lastfmPassword.setText(viewState.getPassword());
                                                          songBatches.setProgress(viewState.getNumberOfSongsPerBatch());
                                                          nowPlaying.setChecked(viewState.getSendNowPlaying());
                                                      });
        disposables.add(disposable);
    }

    @Override
    protected void onPause() {
        disposables.clear();
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
