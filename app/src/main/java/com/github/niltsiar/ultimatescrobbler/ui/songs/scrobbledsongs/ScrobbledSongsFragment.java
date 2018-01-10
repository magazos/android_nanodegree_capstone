package com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.github.niltsiar.ultimatescrobbler.R;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class ScrobbledSongsFragment extends Fragment {

    @Inject
    ScrobbledSongsViewModelFactory scrobbledSongsViewModelFactory;

    @BindView(R.id.scrobbled_songs_recyclerview)
    RecyclerView recyclerView;

    private ScrobbledSongsViewModel viewModel;
    private CompositeDisposable disposables;
    private Unbinder unbinder;

    public ScrobbledSongsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, scrobbledSongsViewModelFactory)
                                      .get(ScrobbledSongsViewModel.class);
        disposables = new CompositeDisposable();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrobbled_songs, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.scrobbled_songs_title);
        Disposable disposable = viewModel.getAdapter()
                                         .subscribe(scrobbledSongsAdapter -> {
                                             recyclerView.setAdapter(scrobbledSongsAdapter);
                                             recyclerView.setHasFixedSize(true);
                                             LinearLayoutManager manager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
                                             recyclerView.setLayoutManager(manager);
                                             recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), manager.getOrientation()));
                                         });
        disposables.add(disposable);
    }

    @Override
    public void onPause() {
        disposables.clear();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}
