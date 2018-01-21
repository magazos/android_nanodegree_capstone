package com.github.niltsiar.ultimatescrobbler.widget;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.cache.database.InfoSongColumns;
import com.github.niltsiar.ultimatescrobbler.cache.database.SongsProvider;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class UltimateScrobblerWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {

            private Cursor data = null;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if (null != data) {
                    data.close();
                }

                long identityToken = Binder.clearCallingIdentity();

                String[] projection = new String[]{InfoSongColumns.TRACK_NAME, InfoSongColumns.ALBUM_ART_URL};

                data = getContentResolver().query(SongsProvider.InfoSong.INFO_SONG, projection, null, null, null);

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (null != data) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return null == data ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (AdapterView.INVALID_POSITION == position || null == data || !data.moveToPosition(position)) {
                    return null;
                }

                String trackName = data.getString(0);
                String albumArtUrl = data.getString(1);

                RemoteViews view = new RemoteViews(getPackageName(), R.layout.widget_item);
                view.setTextViewText(R.id.song, trackName);
                if (!TextUtils.isEmpty(albumArtUrl)) {
                    Picasso.with(getApplicationContext())
                           .load(albumArtUrl)
                           .placeholder(R.drawable.ic_note)
                           .error(R.drawable.ic_note)
                           .into(new Target() {
                               @Override
                               public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                   view.setImageViewBitmap(R.id.album_art, bitmap);
                               }

                               @Override
                               public void onBitmapFailed(Drawable errorDrawable) {

                               }

                               @Override
                               public void onPrepareLoad(Drawable placeHolderDrawable) {

                               }
                           });
                }

                return view;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }
        };
    }
}
