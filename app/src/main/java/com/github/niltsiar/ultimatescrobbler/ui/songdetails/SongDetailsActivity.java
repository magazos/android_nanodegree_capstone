package com.github.niltsiar.ultimatescrobbler.ui.songdetails;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.domain.model.InfoSong;
import com.github.niltsiar.ultimatescrobbler.utils.Utils;
import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import dagger.android.AndroidInjection;
import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
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
    @BindView(R.id.song_title)
    TextView songTitle;
    @BindView(R.id.song_album)
    TextView songAlbum;
    @BindView(R.id.song_author)
    TextView songAuthor;
    @BindView(R.id.tags_layout)
    FlexboxLayout tagsLayout;
    @BindView(R.id.song_info)
    TextView songInfo;
    @BindColor(R.color.colorAccent)
    int accentColor;
    @BindColor(R.color.material_light_white)
    int whiteColor;

    private SongDetailsViewModel songDetailsViewModel;
    private CompositeDisposable disposables;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);
        supportPostponeEnterTransition();
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
            ViewCompat.setTransitionName(songTitle, Utils.getTransitionNameForSongTitle(songId));
            ViewCompat.setTransitionName(songAuthor, Utils.getTransitionNameForSongArtist(songId));
            ViewCompat.setTransitionName(albumArt, Utils.getTransitionNameForSongAlbumArt(songId));
            getSupportLoaderManager().initLoader(0, SongDetailsViewModel.createLoaderBundle(songId), songDetailsViewModel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tagsLayout.removeAllViews();
        Disposable songInfoDisposable = songDetailsViewModel.getInfoSong()
                                                            .subscribe(this::render);
        disposables.add(songInfoDisposable);
    }

    @Override
    protected void onPause() {
        disposables.clear();
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void render(InfoSong infoSong) {
        if (!TextUtils.isEmpty(infoSong.getAlbumArtUrl())) {
            Picasso.with(albumArt.getContext())
                   .load(infoSong.getAlbumArtUrl())
                   .placeholder(R.drawable.ic_note)
                   .error(R.drawable.ic_note)
                   /*.into(albumArt, new Callback() {
                       @Override
                       public void onSuccess() {
                           supportStartPostponedEnterTransition();
                       }

                       @Override
                       public void onError() {
                           supportStartPostponedEnterTransition();
                       }
                   });*/
                   .into(new Target() {
                       @Override
                       public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                           finishRendering(infoSong, bitmap);
                           supportStartPostponedEnterTransition();
                       }

                       @Override
                       public void onBitmapFailed(Drawable errorDrawable) {
                           fillViews(infoSong, createChipCloudConfig(whiteColor, accentColor));
                           supportStartPostponedEnterTransition();
                       }

                       @Override
                       public void onPrepareLoad(Drawable placeHolderDrawable) {

                       }
                   });
        } else {
            fillViews(infoSong, createChipCloudConfig(whiteColor, accentColor));
            supportStartPostponedEnterTransition();
        }
    }

    private void finishRendering(InfoSong infoSong, Bitmap bitmap) {
        Palette.from(bitmap)
               .generate(palette -> {
                   Palette.Swatch swatch = palette.getDominantSwatch();
                   if (null != swatch) {
                       toolbar.setBackgroundColor(swatch.getRgb());
                       toolbar.setTitleTextColor(swatch.getTitleTextColor());
                       DrawableCompat.setTint(toolbar.getNavigationIcon(), swatch.getTitleTextColor());
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                           getWindow().setStatusBarColor(palette.getDarkMutedColor(0xFFF));
                       }
                       fillViews(infoSong, createChipCloudConfig(swatch.getBodyTextColor(), swatch.getRgb()));
                   } else {
                       fillViews(infoSong, createChipCloudConfig(whiteColor, accentColor));
                   }
                   albumArt.setImageBitmap(bitmap);
               });
    }

    private void fillViews(InfoSong infoSong, ChipCloudConfig chipCloudConfig) {
        songTitle.setText(infoSong.getTrackName());
        songAlbum.setText(infoSong.getAlbum());
        songAuthor.setText(infoSong.getArtist());

        boolean emptyTags = (1 == infoSong.getTags()
                                          .size() && TextUtils.isEmpty(infoSong.getTags()
                                                                               .get(0)));
        if (!emptyTags) {
            ChipCloud chipCloud = new ChipCloud(this, tagsLayout, chipCloudConfig);
            chipCloud.addChips(infoSong.getTags());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            songInfo.setText(Html.fromHtml(infoSong.getWikiContent(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            songInfo.setText(Html.fromHtml(infoSong.getWikiContent()));
        }
    }

    private ChipCloudConfig createChipCloudConfig(int chipTextColor, int chipColor) {
        return new ChipCloudConfig().selectMode(ChipCloud.SelectMode.none)
                                    .uncheckedChipColor(chipColor)
                                    .uncheckedTextColor(chipTextColor)
                                    .useInsetPadding(true);
    }
}
