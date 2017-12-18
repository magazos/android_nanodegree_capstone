package com.github.niltsiar.ultimatescrobbler.ui.songs.playedsongs;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.niltsiar.ultimatescrobbler.R;
import dagger.android.support.AndroidSupportInjection;
import javax.inject.Inject;

public class PlayedSongsFragment extends Fragment {

    @Inject
    PlayedSongsViewModelFactory playedSongsViewModelFactory;
    PlayedSongsViewModel viewModel;

    public PlayedSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, playedSongsViewModelFactory)
                                      .get(PlayedSongsViewModel.class);
        getLoaderManager().initLoader(0, null, viewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_played_songs, container, false);
    }
}
