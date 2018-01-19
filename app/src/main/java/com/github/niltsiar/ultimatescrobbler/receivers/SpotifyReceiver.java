package com.github.niltsiar.ultimatescrobbler.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.github.niltsiar.ultimatescrobbler.domain.model.PlayedSong;
import com.jakewharton.rxrelay2.PublishRelay;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.threeten.bp.Instant;
import timber.log.Timber;

public class SpotifyReceiver extends BroadcastReceiver {

    private static IntentFilter spotifyIntents;
    private PublishRelay<PlayedSong> playedSongs;
    private PublishRelay<PlayedSong> nowPlaying;
    private PublishRelay<PlayedSong> newSong;

    private static int SONG_DEBOUNCE_MS = 10000;
    private static float PERCENTAGE_TO_SCROBBLE = 0.1f;

    private final class BroadcastTypes {
        static final String SPOTIFY_PACKAGE = "com.spotify.music";
        static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
        static final String QUEUE_CHANGED = SPOTIFY_PACKAGE + ".queuechanged";
        static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";
    }

    public static IntentFilter getSpotifyIntents() {
        if (null == spotifyIntents) {
            spotifyIntents = new IntentFilter();
            spotifyIntents.addAction(BroadcastTypes.METADATA_CHANGED);
        }
        return spotifyIntents;
    }

    @Inject
    public SpotifyReceiver() {
        playedSongs = PublishRelay.create();
        nowPlaying = PublishRelay.create();
        newSong = PublishRelay.create();

        getNewSong().subscribe(nowPlaying);
        getNewSong().switchMap(playedSong -> Observable.just(playedSong)
                                                       .delay((int) Math.ceil(playedSong.getLength() * PERCENTAGE_TO_SCROBBLE), TimeUnit.MILLISECONDS))
                    .subscribe(playedSongs);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        long timeSent = intent.getLongExtra("timeSent", 0);

        Timber.i("Action: %s", action);
        Timber.i("Extras: %s", intent.getExtras()
                                     .toString());

        if (action.equals(BroadcastTypes.METADATA_CHANGED)) {
            String trackId = intent.getStringExtra("id");
            String artistName = intent.getStringExtra("artist");
            String albumName = intent.getStringExtra("album");
            String trackName = intent.getStringExtra("track");
            int length = intent.getIntExtra("length", 0);

            //Discard if it is an old notification
            if (System.currentTimeMillis() - timeSent > length) {
                return;
            }

            try {
                PlayedSong playedSong = PlayedSong.builder()
                                                  .setArtistName(artistName)
                                                  .setAlbumName(albumName)
                                                  .setTrackName(trackName)
                                                  .setLength(length)
                                                  .setTimestamp(Instant.ofEpochMilli(timeSent))
                                                  .build();

                newSong.accept(playedSong);
            } catch (NullPointerException ex) {
                Timber.d("Ignoring malformed song");
            }
        } else if (action.equals(BroadcastTypes.PLAYBACK_STATE_CHANGED)) {
            boolean playing = intent.getBooleanExtra("playing", false);
            int positionInMs = intent.getIntExtra("playbackPosition", 0);
        }
    }

    public Observable<PlayedSong> getPlayedSongs() {
        return playedSongs;
    }

    public Observable<PlayedSong> getNowPlayingSong() {
        return nowPlaying;
    }

    private Observable<PlayedSong> getNewSong() {
        return newSong.debounce(SONG_DEBOUNCE_MS, TimeUnit.MILLISECONDS);
    }
}
