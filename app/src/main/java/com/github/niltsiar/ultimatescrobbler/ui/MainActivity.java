package com.github.niltsiar.ultimatescrobbler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.github.niltsiar.ultimatescrobbler.BuildConfig;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.SpotifyReceiver;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.mobilesession.RequestMobileSessionTokenUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.GetPlayedSongsUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.SavePlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.model.Credentials;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.services.ScrobblePlayedSongsService;
import com.github.niltsiar.ultimatescrobbler.services.SendNowPlayingService;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import javax.inject.Inject;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Inject
    RequestMobileSessionTokenUseCase requestMobileSessionTokenUseCase;

    @Inject
    SavePlayedSong savePlayedSongUseCase;

    @Inject
    GetPlayedSongsUseCase getStoredPlayedSongsUseCase;

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

        DisposableObserver<Pair<Long, PlayedSong>> playedSongDisposable = new DisposableObserver<Pair<Long, PlayedSong>>() {

            @Override
            public void onNext(Pair<Long, PlayedSong> longPlayedSongPair) {
                Long count = longPlayedSongPair.first;
                PlayedSong playedSong = longPlayedSongPair.second;
                Timber.i("%d stored songs", count);
                Job sendNowPlayingJob = SendNowPlayingService.createJob(dispatcher, playedSong.getId());
                dispatcher.mustSchedule(sendNowPlayingJob);
                if (0 == count % 2) {
                    Job scrobbledStoredSongs = ScrobblePlayedSongsService.createJob(dispatcher);
                    dispatcher.mustSchedule(scrobbledStoredSongs);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        playedSongsDisposables.add(playedSongDisposable);

        spotifyReceiver.getPlayedSongs()
                       .flatMap(playedSong -> savePlayedSongUseCase.execute(playedSong)
                                                                   .flatMapObservable(countStoredSongs -> Observable.just(new Pair<>(countStoredSongs, playedSong))))
                       .subscribe(playedSongDisposable);

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
