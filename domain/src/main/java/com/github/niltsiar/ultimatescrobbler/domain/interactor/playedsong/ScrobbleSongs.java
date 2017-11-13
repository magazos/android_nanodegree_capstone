package com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.ObservableUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.model.ScrobbledSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import java.util.List;
import javax.annotation.Nonnull;

public class ScrobbleSongs extends ObservableUseCase<ScrobbledSong, List<PlayedSong>> {

    private ScrobblerRepository scrobblerRepository;

    public ScrobbleSongs(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Observable<ScrobbledSong> buildUseCaseObservable(@Nonnull List<PlayedSong> playedSongs) {
        return scrobblerRepository.scrobblePlayedSongs(playedSongs);
    }
}
