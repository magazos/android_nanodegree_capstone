package com.github.niltsiar.ultimatescrobbler.domain.interactor.songinformation;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.CompletableUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import javax.annotation.Nonnull;

public class SaveSongInformationUseCase extends CompletableUseCase<InfoSong> {

    private ScrobblerRepository scrobblerRepository;

    public SaveSongInformationUseCase(ScrobblerRepository scrobblerRepository, Scheduler executionScheduler, Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
    }

    @Override
    protected Completable buildUseCaseObservable(@Nonnull InfoSong infoSong) {
        return scrobblerRepository.saveSongInformation(infoSong);
    }
}
