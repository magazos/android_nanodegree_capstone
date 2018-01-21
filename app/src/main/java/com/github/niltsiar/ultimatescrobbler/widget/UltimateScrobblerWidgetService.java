package com.github.niltsiar.ultimatescrobbler.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.cache.database.InfoSongColumns;
import com.github.niltsiar.ultimatescrobbler.cache.database.SongsProvider;
import com.squareup.picasso.Picasso;
import java.io.IOException;

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
                Bitmap bitmap;
                if (!TextUtils.isEmpty(albumArtUrl)) {
                    try {
                        bitmap = Picasso.with(getApplicationContext())
                                        .load(albumArtUrl)
                                        .placeholder(R.drawable.ic_note)
                                        .error(R.drawable.ic_note)
                                        .get();
                    } catch (IOException e) {
                        bitmap = getBitmapFromVectorDrawable(getApplicationContext(), R.drawable.ic_note);
                    }

                } else {
                    bitmap = getBitmapFromVectorDrawable(getApplicationContext(), R.drawable.ic_note);
                }
                view.setImageViewBitmap(R.id.album_art, bitmap);

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

            private Bitmap getBitmapFromVectorDrawable(Context context, @DrawableRes int drawableId) {
                Drawable drawable = ContextCompat.getDrawable(context, drawableId);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    drawable = (DrawableCompat.wrap(drawable)).mutate();
                }

                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);

                return bitmap;
            }
        };
    }
}
