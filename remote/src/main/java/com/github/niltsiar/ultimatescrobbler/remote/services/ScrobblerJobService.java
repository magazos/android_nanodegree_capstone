package com.github.niltsiar.ultimatescrobbler.remote.services;

import android.os.Bundle;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.github.niltsiar.ultimatescrobbler.remote.ScrobblerService;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

public abstract class ScrobblerJobService extends JobService {

    @Inject
    ScrobblerService scrobblerService;

    protected CompositeDisposable compositeDisposable;

    protected static final String PARAMS = "params";
    protected static final String RESPONSE_FORMAT = "json";

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        compositeDisposable.clear();
        return false;
    }

    protected void finishJob(JobParameters job, boolean needsReschedule) {
        compositeDisposable.clear();
        jobFinished(job, needsReschedule);
    }

    protected static Bundle mapToBundle(Map<String, String> params) {
        Bundle extras = new Bundle();
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(Map.class, String.class, String.class);
        JsonAdapter<Map<String, String>> jsonAdapter = moshi.adapter(type);
        String jsonParams = jsonAdapter.toJson(params);
        extras.putString(PARAMS, jsonParams);
        return extras;
    }

    protected static Map<String, String> bundleToMap(Bundle bundle) {
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
