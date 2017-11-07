package com.github.niltsiar.ultimatescrobbler.remote.services;

import android.os.Bundle;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.github.niltsiar.ultimatescrobbler.remote.model.ScrobbledSongModel;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.Map;
import java.util.SortedMap;
import timber.log.Timber;

public class SendNowPlayingService extends ScrobblerJobService {

    @Override
    public boolean onStartJob(JobParameters job) {
        Map<String, String> params = bundleToMap(job.getExtras());

        DisposableSingleObserver<ScrobbledSongModel> observer = new DisposableSingleObserver<ScrobbledSongModel>() {
            @Override
            public void onSuccess(ScrobbledSongModel scrobbledSongModel) {
                Timber.i(scrobbledSongModel.toString());
                finishJob(job, false);
            }

            @Override
            public void onError(Throwable e) {

            }
        };

        Disposable disposable = scrobblerService.updateNowPlaying(params, RESPONSE_FORMAT)
                                                .subscribeOn(Schedulers.io())
                                                .subscribeWith(observer);
        compositeDisposable.add(disposable);

        return true;
    }

    public static Job createJob(FirebaseJobDispatcher dispatcher, SortedMap<String, String> params) {
        Bundle extras = mapToBundle(params);

        return dispatcher.newJobBuilder()
                         .setService(SendNowPlayingService.class)
                         .setTag(SendNowPlayingService.class.getName())
                         .setRecurring(false)
                         .setLifetime(30)
                         .setTrigger(Trigger.NOW)
                         .setReplaceCurrent(true)
                         .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                         .setConstraints(Constraint.ON_ANY_NETWORK)
                         .setExtras(extras)
                         .build();
    }
}
