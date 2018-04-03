package com.spade.nrc.media.player;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.spade.nrc.utils.Constants;

/**
 * Created by Ayman Abouzeid on 1/20/18.
 */

public class MediaPlayerService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(202, null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Show show = intent.getParcelableExtra(Constants.EXTRA_SHOW);
//        String streamURL = intent.getStringExtra(Constants.EXTRA_CHANNEL_URL);
//        int channelID = intent.getIntExtra(Constants.EXTRA_CHANNEL_ID, 1);
        String action = intent.getAction();

        MediaPlayerController mediaPlayerController = MediaPlayerController.getInstance();
        mediaPlayerController.setMediaPlayerService(getApplicationContext(), this);

        if (action != null) {
            if (action.equals(Constants.ACTION_PAUSE)) {
                stopSelf();
            } else {
//                MediaPlayerTrack mediaPlayerTrack = new MediaPlayerTrack(Constants.NAGHAM_ID, "https://ahmsamir.radioca.st/stream");
//                mediaPlayerController.doMediaAction(mediaPlayerTrack);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

}
