package com.github.niltsiar.ultimatescrobbler.domain.interactor.playedsong;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.SingleUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import java.util.List;
import javax.annotation.Nullable;

public class GetPlayedSongsUseCase extends SingleUseCase<List<PlayedSong>, Void> {

    private ScrobblerRepository scrobblerRepository;

    public GetPlayedSongsUseCase(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Single<List<PlayedSong>> buildUseCaseObservable(@Nullable Void param) {
        return scrobblerRepository.getStoredPlayedSongs();
    }
}
