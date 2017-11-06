package com.github.niltsiar.ultimatescrobbler.domain.interactor.saveplayedsong;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.CompletableUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.inject.Inject;

public class SavePlayedSong extends CompletableUseCase<PlayedSong> {

    private ScrobblerRepository scrobblerRepository;

    @Inject
    public SavePlayedSong(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Completable buildUseCaseObservable(PlayedSong currentSong) {
        return scrobblerRepository.savePlayedSong(currentSong);
    }
}
