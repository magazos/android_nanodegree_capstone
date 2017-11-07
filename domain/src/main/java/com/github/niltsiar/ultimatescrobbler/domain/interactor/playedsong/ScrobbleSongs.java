package com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.CompletableUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import java.util.List;

public class ScrobbleSongs extends CompletableUseCase<List<PlayedSong>> {

    private ScrobblerRepository scrobblerRepository;

    public ScrobbleSongs(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Completable buildUseCaseObservable(List<PlayedSong> playedSongs) {
        return scrobblerRepository.scrobblePlayedSongs(playedSongs);
    }
}
