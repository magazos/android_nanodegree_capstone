package com.github.niltsiar.ultimatescrobbler.utils.rxindicatorseekbar;

import android.annotation.SuppressLint;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.warkiz.widget.IndicatorSeekBar;

import static com.jakewharton.rxbinding2.internal.Preconditions.checkNotNull;

/**
 * Based on RxSeekBar from RxBinding
 */
public class RxIndicatorSeekBar {

    @SuppressLint("RestrictedApi")
    @CheckResult
    @NonNull
    public static InitialValueObservable<Integer> changes(@NonNull IndicatorSeekBar view) {
        checkNotNull(view, "view == null");
        return new IndicatorSeekBarChangeObservable(view, null);
    }

    @SuppressLint("RestrictedApi")
    @CheckResult
    @NonNull
    public static InitialValueObservable<Integer> userChanges(@NonNull IndicatorSeekBar view) {
        checkNotNull(view, "view == null");
        return new IndicatorSeekBarChangeObservable(view, true);
    }

    @SuppressLint("RestrictedApi")
    @CheckResult
    @NonNull
    public static InitialValueObservable<Integer> systemChanges(@NonNull IndicatorSeekBar view) {
        checkNotNull(view, "view == null");
        return new IndicatorSeekBarChangeObservable(view, false);
    }

    private RxIndicatorSeekBar() {
        throw new AssertionError("No instances.");
    }
}
