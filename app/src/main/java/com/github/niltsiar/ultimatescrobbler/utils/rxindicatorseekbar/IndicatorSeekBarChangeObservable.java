package com.github.niltsiar.ultimatescrobbler.utils.rxindicatorseekbar;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.warkiz.widget.IndicatorSeekBar;
import io.reactivex.Observer;
import io.reactivex.android.MainThreadDisposable;

import static com.jakewharton.rxbinding2.internal.Preconditions.checkMainThread;

/**
 * Based on SeekBarChangeObservable from RxBinding
 */
final class IndicatorSeekBarChangeObservable extends InitialValueObservable<Integer> {
    private final IndicatorSeekBar view;
    @Nullable
    private final Boolean shouldBeFromUser;

    IndicatorSeekBarChangeObservable(IndicatorSeekBar view, @Nullable Boolean shouldBeFromUser) {
        this.view = view;
        this.shouldBeFromUser = shouldBeFromUser;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void subscribeListener(Observer<? super Integer> observer) {
        if (!checkMainThread(observer)) {
            return;
        }
        Listener listener = new Listener(view, shouldBeFromUser, observer);
        view.setOnSeekChangeListener(listener);
        observer.onSubscribe(listener);
    }

    @Override
    protected Integer getInitialValue() {
        return view.getProgress();
    }

    static final class Listener extends MainThreadDisposable implements IndicatorSeekBar.OnSeekBarChangeListener {
        private final IndicatorSeekBar view;
        private final Boolean shouldBeFromUser;
        private final Observer<? super Integer> observer;

        Listener(IndicatorSeekBar view, Boolean shouldBeFromUser, Observer<? super Integer> observer) {
            this.view = view;
            this.shouldBeFromUser = shouldBeFromUser;
            this.observer = observer;
        }

        @Override
        protected void onDispose() {
            view.setOnSeekChangeListener(this);
        }

        @Override
        public void onProgressChanged(IndicatorSeekBar seekBar, int progress, float progressFloat, boolean fromUserTouch) {
            if (!isDisposed() && (shouldBeFromUser == null || shouldBeFromUser == fromUserTouch)) {
                observer.onNext(progress);
            }
        }

        @Override
        public void onSectionChanged(IndicatorSeekBar seekBar, int thumbPosOnTick, String textBelowTick, boolean fromUserTouch) {

        }

        @Override
        public void onStartTrackingTouch(IndicatorSeekBar seekBar, int thumbPosOnTick) {

        }

        @Override
        public void onStopTrackingTouch(IndicatorSeekBar seekBar) {

        }
    }
}
