package com.github.niltsiar.ultimatescrobbler.domain.interactor.scrobble;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.CompletableUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class SendNowPlaying extends CompletableUseCase<PlayedSong> {

    private ScrobblerRepository scrobblerRepository;

    public SendNowPlaying(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Completable buildUseCaseObservable(PlayedSong currentSong) {
        return scrobblerRepository.sendNowPlaying(currentSong);
    }
}
