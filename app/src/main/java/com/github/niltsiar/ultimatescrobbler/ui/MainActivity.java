package com.github.niltsiar.ultimatescrobbler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.github.niltsiar.ultimatescrobbler.BuildConfig;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.SpotifyReceiver;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession.RequestMobileSessionTokenUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.GetStoredPlayedSongs;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.SavePlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.ScrobbleSongs;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.songinformation.GetSongInformation;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.services.SendNowPlayingService;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    RequestMobileSessionTokenUseCase requestMobileSessionTokenUseCase;

    @Inject
    SavePlayedSong savePlayedSongUseCase;

    @Inject
    GetStoredPlayedSongs getStoredPlayedSongsUseCase;

    @Inject
    ScrobbleSongs scrobbleSongsUseCase;

    @Inject
    GetSongInformation getSongInformationUseCase;

    @Inject
    FirebaseJobDispatcher dispatcher;

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

        DisposableObserver<PlayedSong> savePlayedSong = new DisposableObserver<PlayedSong>() {
            @Override
            public void onNext(PlayedSong playedSong) {
                Disposable disposable = savePlayedSongUseCase.execute(playedSong)
                                                             .subscribe(count -> {
                                                                 Timber.i("%d stored songs", count);
                                                                 Job sendNowPlayingJob = SendNowPlayingService.createJob(dispatcher,
                                                                                                                         playedSong.getId());
                                                                 dispatcher.mustSchedule(sendNowPlayingJob);
                                                             });
                playedSongsDisposables.add(disposable);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        playedSongsDisposables.add(savePlayedSong);

        spotifyReceiver.getPlayedSongs()
                       .subscribe(savePlayedSong);

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
