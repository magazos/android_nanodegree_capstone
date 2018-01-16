package com.github.niltsiar.ultimatescrobbler.ui.songdetails;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.niltsiar.ultimatescrobbler.R;
import com.squareup.picasso.Picasso;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class SongDetailsActivity extends AppCompatActivity {

    @Inject
    SongDetailsViewModelFactory songDetailsViewModelFactory;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.album_art)
    ImageView albumArt;

    private SongDetailsViewModel songDetailsViewModel;
    private CompositeDisposable disposables;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        songDetailsViewModel = ViewModelProviders.of(this, songDetailsViewModelFactory)
                                                 .get(SongDetailsViewModel.class);

        disposables = new CompositeDisposable();

        if (null == savedInstanceState && null != getIntent() && null != getIntent().getData()) {
            String songId = getIntent().getData()
                                       .getLastPathSegment();
            getSupportLoaderManager().initLoader(0, SongDetailsViewModel.createLoaderBundle(songId), songDetailsViewModel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Disposable songInfoDisposable = songDetailsViewModel.getInfoSong()
                                                            .subscribe(infoSong -> {
                                                                if (!TextUtils.isEmpty(infoSong.getAlbumArtUrl())) {
                                                                    Picasso.with(albumArt.getContext())
                                                                           .load(infoSong.getAlbumArtUrl())
                                                                           .placeholder(R.drawable.ic_note)
                                                                           .error(R.drawable.ic_note)
                                                                           .into(albumArt);
                                                                }
                                                            });
        disposables.add(songInfoDisposable);
    }

    @Override
    protected void onPause() {
        disposables.clear();
        super.onPause();
    }
}
