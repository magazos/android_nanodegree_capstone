package com.github.niltsiar.ultimatescrobbler.services;

import android.support.v4.util.Pair;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.DeletePlayedSongUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.GetPlayedSongsUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong.ScrobbleSongsUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.songinformation.GetSongInformationUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.interactor.songinformation.SaveSongInformationUseCase;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import timber.log.Timber;

public class ScrobblePlayedSongsService extends JobService {

    @Inject
    GetPlayedSongsUseCase getStoredPlayedSongsUseCase;

    @Inject
    ScrobbleSongsUseCase scrobbleSongsUseCase;

    @Inject
    GetSongInformationUseCase getSongInformationUseCase;

    @Inject
    SaveSongInformationUseCase saveSongInformationUseCase;

    @Inject
    DeletePlayedSongUseCase deletePlayedSongUseCase;

    private CompositeDisposable disposables;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
        disposables = new CompositeDisposable();
    }

    @Override
    public boolean onStartJob(JobParameters job) {

        Disposable disposable = getStoredPlayedSongsUseCase.execute(null)
                                                           .flatMapObservable(songs -> scrobbleSongsUseCase.execute(songs)
                                                                                                           .zipWith(Observable.interval(500, TimeUnit.MILLISECONDS),
                                                                                                                    (scrobbledSong, index) -> new Pair<>(songs.get(index.intValue()), scrobbledSong)))
                                                           .flatMapSingle(pair -> getSongInformationUseCase.execute(pair.second)
                                                                                                           .map(infoSong -> new Pair<>(pair.first, infoSong)))
                                                           .doOnNext(pair -> Timber.i(pair.toString()))
                                                           .flatMap(pair -> saveSongInformationUseCase.execute(pair.second)
                                                                                                      .andThen(Observable.just(pair.first)))
                                                           .flatMapCompletable(deletePlayedSongUseCase::execute)
                                                           .subscribe(() -> finishJob(job, false), Timber::e);
        disposables.add(disposable);
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

    public static Job createJob(FirebaseJobDispatcher dispatcher) {
        return dispatcher.newJobBuilder()
                         .setService(ScrobblePlayedSongsService.class)
                         .setTag(ScrobblePlayedSongsService.class.getName())
                         .setRecurring(false)
                         .setLifetime(Lifetime.FOREVER)
                         .setTrigger(Trigger.NOW)
                         .setReplaceCurrent(false)
                         .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                         .setConstraints(Constraint.ON_ANY_NETWORK)
                         .build();
    }
}
