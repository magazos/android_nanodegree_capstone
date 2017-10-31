package com.github.niltsiar.ultimatescrobbler.remote.services;

import android.os.Bundle;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.github.niltsiar.ultimatescrobbler.remote.ScrobblerService;
import com.github.niltsiar.ultimatescrobbler.remote.model.ScrobbledSongModel;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import javax.inject.Inject;
import timber.log.Timber;

public class SendNowPlayingService extends JobService {

    private static final String PARAMS = "params";
    private static final String RESPONSE_FORMAT = "json";
    private static final String JOB_NAME = "SendNowPlayingService";

    @Inject
    ScrobblerService scrobblerService;

    private Disposable disposable;

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public boolean onStartJob(JobParameters job) {
        Map<String, String> params = bundleToMap(job.getExtras());

        disposable = scrobblerService.updateNowPlaying(params, RESPONSE_FORMAT)
                                     .subscribeOn(Schedulers.io())
                                     .subscribeWith(new DisposableSingleObserver<ScrobbledSongModel>() {
                                         @Override
                                         public void onSuccess(ScrobbledSongModel scrobbledSongModel) {
                                             Timber.i(scrobbledSongModel.toString());
                                             finishJob(job, false);
                                         }

                                         @Override
                                         public void onError(Throwable e) {

                                         }
                                     });

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        return false;
    }

    private void finishJob(JobParameters job, boolean needsReschedule) {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        jobFinished(job, needsReschedule);
    }

    public static Job createJob(FirebaseJobDispatcher dispatcher, SortedMap<String, String> params) {
        Bundle extras = mapToBundle(params);

        return dispatcher.newJobBuilder()
                         .setService(SendNowPlayingService.class)
                         .setTag(JOB_NAME)
                         .setRecurring(false)
                         .setLifetime(30)
                         .setTrigger(Trigger.executionWindow(0, 5))
                         .setReplaceCurrent(true)
                         .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                         .setConstraints(Constraint.ON_ANY_NETWORK)
                         .setExtras(extras)
                         .build();
    }

    private static Bundle mapToBundle(Map<String, String> params) {
        Bundle extras = new Bundle();
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(Map.class, String.class, String.class);
        JsonAdapter<Map<String, String>> jsonAdapter = moshi.adapter(type);
        String jsonParams = jsonAdapter.toJson(params);
        extras.putString(PARAMS, jsonParams);
        return extras;
    }

    private static Map<String, String> bundleToMap(Bundle bundle) {
        Map<String, String> params = new HashMap<>();

        if (!bundle.containsKey(PARAMS)) {
            return params;
        }

        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(Map.class, String.class, String.class);
        JsonAdapter<Map<String, String>> jsonAdapter = moshi.adapter(type);
        try {
            Map<String, String> map = jsonAdapter.fromJson(bundle.getString(PARAMS));
            params = new HashMap<>(map);
        } catch (IOException e) {
            params = new HashMap<>();
        }
        return params;
    }
}
