package com.github.niltsiar.ultimatescrobbler.domain.interactor.songinformation;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.SingleUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;
import com.github.niltsiar.ultimatescrobbler.domain.model.ScrobbledSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import javax.annotation.Nonnull;

public class GetSongInformationUseCase extends SingleUseCase<InfoSong, ScrobbledSong> {

    private ScrobblerRepository scrobblerRepository;

    public GetSongInformationUseCase(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Single<InfoSong> buildUseCaseObservable(@Nonnull ScrobbledSong song) {
        return scrobblerRepository.getSongInformation(song);
    }
}
