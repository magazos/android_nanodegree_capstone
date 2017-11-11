package com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.SingleUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class SavePlayedSong extends SingleUseCase<Long, PlayedSong> {

    private ScrobblerRepository scrobblerRepository;

    public SavePlayedSong(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Single<Long> buildUseCaseObservable(PlayedSong currentSong) {
        return scrobblerRepository.savePlayedSong(currentSong)
                                  .andThen(scrobblerRepository.countStoredPlayedSongs());
    }
}
