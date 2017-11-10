package com.github.niltsiar.ultimatescrobbler.remote.services;

import android.os.Bundle;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.github.niltsiar.ultimatescrobbler.remote.model.InfoSongModel;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;
import timber.log.Timber;

public class GetInfoService extends ScrobblerJobService {

    private static final String TIMESTAMP_PARAM = "extra_timestamp";

    @Override
    public boolean onStartJob(JobParameters job) {
        long timestamp = 0;
        Bundle extras = job.getExtras();
        if (extras.containsKey(TIMESTAMP_PARAM)) {
            timestamp = extras.getLong(TIMESTAMP_PARAM);
        }
        Map<String, String> params = bundleToMap(job.getExtras());

        DisposableSingleObserver<InfoSongModel> observer = new DisposableSingleObserver<InfoSongModel>() {
            @Override
            public void onSuccess(InfoSongModel infoSongModel) {
                Timber.i(infoSongModel.toString());
                finishJob(job, false);
            }

            @Override
            public void onError(Throwable e) {

            }
        };

        Disposable disposable = scrobblerService.getInfo(params, RESPONSE_FORMAT)
                                                .subscribeOn(Schedulers.io())
                                                .subscribeWith(observer);
        compositeDisposable.add(disposable);

        return false;
    }

    public static Job createJob(FirebaseJobDispatcher dispatcher, SortedMap<String, String> params, long timestamp) {
        Bundle extras = mapToBundle(params);
        extras.putLong(TIMESTAMP_PARAM, timestamp);

        return dispatcher.newJobBuilder()
                         .setService(GetInfoService.class)
                         .setTag(UUID.randomUUID()
                                     .toString())
                         .setRecurring(false)
                         .setLifetime(Lifetime.FOREVER)
                         .setTrigger(Trigger.NOW)
                         .setReplaceCurrent(false)
                         .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                         .setConstraints(Constraint.ON_ANY_NETWORK)
                         .setExtras(extras)
                         .build();
    }
}
