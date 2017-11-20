package com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.CompletableUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.annotation.Nonnull;

public class DeletePlayedSongUseCase extends CompletableUseCase<PlayedSong> {

    private ScrobblerRepository scrobblerRepository;

    public DeletePlayedSongUseCase(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Completable buildUseCaseObservable(@Nonnull PlayedSong playedSong) {
        return scrobblerRepository.deleteStoredPlayedSong(playedSong);
    }
}
