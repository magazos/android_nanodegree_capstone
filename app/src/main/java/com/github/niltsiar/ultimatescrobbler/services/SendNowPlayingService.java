package com.github.niltsiar.ultimatescrobbler.services;

import android.os.Bundle;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.GetPlayedSongUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.SendNowPlayingUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import javax.inject.Inject;
import timber.log.Timber;

public class SendNowPlayingService extends JobService {

    @Inject
    SendNowPlayingUseCase sendNowPlayingUseCase;

    @Inject
    GetPlayedSongUseCase getPlayedSongUseCase;

    private CompositeDisposable disposables;

    private static final String NOW_PLAYING_ID = "nowPlayingId";
    private static final int LIFETIME = 30;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
        disposables = new CompositeDisposable();
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        Bundle extras = job.getExtras();
        if (!extras.containsKey(NOW_PLAYING_ID)) {
            return false;
        }

        String songId = extras.getString(NOW_PLAYING_ID);

        DisposableCompletableObserver scrobblerObserver = new DisposableCompletableObserver() {

            @Override
            public void onComplete() {
                finishJob(job, false);
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
            }
        };

        disposables.add(scrobblerObserver);

        DisposableSingleObserver<PlayedSong> songObserver = new DisposableSingleObserver<PlayedSong>() {

            @Override
            public void onSuccess(PlayedSong playedSong) {
                sendNowPlayingUseCase.execute(playedSong)
                                     .subscribe(scrobblerObserver);
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e);
            }
        };

        disposables.add(songObserver);

        getPlayedSongUseCase.execute(songId)
                            .subscribe(songObserver);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        disposables.clear();
        return false;
    }

    protected void finishJob(JobParameters job, boolean needsReschedule) {
        disposables.clear();
        jobFinished(job, needsReschedule);
    }

    public static Job createJob(FirebaseJobDispatcher dispatcher, String nowPlayingId) {
        Bundle extras = new Bundle();
        extras.putString(NOW_PLAYING_ID, nowPlayingId);

        return dispatcher.newJobBuilder()
                         .setService(SendNowPlayingService.class)
                         .setTag(SendNowPlayingService.class.getName())
                         .setRecurring(false)
                         .setLifetime(LIFETIME)
                         .setTrigger(Trigger.NOW)
                         .setReplaceCurrent(true)
                         .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                         .setConstraints(Constraint.ON_ANY_NETWORK)
                         .setExtras(extras)
                         .build();
    }
}
