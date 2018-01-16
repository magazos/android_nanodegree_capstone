package com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.SingleUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.annotation.Nonnull;

public class GetCurrentSongUseCase extends SingleUseCase<PlayedSong, String> {

    private ScrobblerRepository scrobblerRepository;

    public GetCurrentSongUseCase(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Single<PlayedSong> buildUseCaseObservable(@Nonnull String id) {
        return scrobblerRepository.getCurrentSong(id);
    }
}
