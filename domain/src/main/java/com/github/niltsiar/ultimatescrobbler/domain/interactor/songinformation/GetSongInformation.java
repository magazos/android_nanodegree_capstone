package com.github.niltsiar.ultimatescrobbler.domain.interactor.songinformation;

import com.github.niltsiar.ultimatescrobbler.domain.interactor.CompletableUseCase;
import com.github.niltsiar.ultimatescrobbler.domain.model.ScrobbledSong;
import com.github.niltsiar.ultimatescrobbler.domain.repository.ScrobblerRepository;
import io.reactivex.Completable;
import io.reactivex.Scheduler;

public class GetSongInformation extends CompletableUseCase<ScrobbledSong> {

    private ScrobblerRepository scrobblerRepository;
    private String username;

    public GetSongInformation(ScrobblerRepository scrobblerRepository,
            String username,
            Scheduler executionScheduler,
            Scheduler postExecutionScheduler) {
        super(executionScheduler, postExecutionScheduler);

        this.scrobblerRepository = scrobblerRepository;
        this.username = username;
    }

    @Override
    protected Completable buildUseCaseObservable(ScrobbledSong song) {
        return scrobblerRepository.getSongInformation(song, username);
    }
}
