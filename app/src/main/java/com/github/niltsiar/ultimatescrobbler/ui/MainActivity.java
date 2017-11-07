package com.github.niltsiar.ultimatescrobbler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.niltsiar.ultimatescrobbler.BuildConfig;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.SpotifyReceiver;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession.RequestMobileSessionToken;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.SavePlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.SendNowPlaying;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    RequestMobileSessionToken requestMobileSessionTokenUseCase;

    @Inject
    SendNowPlaying sendNowPlayingUseCase;

    @Inject
    SavePlayedSong savePlayedSongUseCase;

    SpotifyReceiver spotifyReceiver;
    CompositeDisposable playedSongsDisposables;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        spotifyReceiver = new SpotifyReceiver();
        playedSongsDisposables = new CompositeDisposable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        playedSongsDisposables.add(spotifyReceiver.getPlayedSongs()
                                                  .subscribe(playedSong -> sendNowPlayingUseCase.execute(playedSong)
                                                                                                .subscribe()));
        playedSongsDisposables.add(spotifyReceiver.getPlayedSongs()
                                                  .subscribe(playedSong -> savePlayedSongUseCase.execute(playedSong)
                                                                                                .subscribe(count -> Timber.i("%d stored songs",
                                                                                                                             count))));
        registerReceiver(spotifyReceiver, SpotifyReceiver.getSpotifyIntents());
    }

    @Override
    protected void onStop() {
        playedSongsDisposables.clear();
        unregisterReceiver(spotifyReceiver);
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
