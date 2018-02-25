package com.spade.nrc.media.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.spade.nrc.utils.Constants;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Ayman Abouzeid on 1/20/18.
 */

public class MediaPlayerController implements MediaPlayer.OnPreparedListener {

    private static final int STATE_PLAYING = 900;
    private static final int STATE_PAUSED = 901;
    private int mediaState = STATE_PAUSED;
    private static MediaPlayerController mediaPlayerController;
    private MediaPlayer mediaPlayer;
    private HashMap<Integer, Integer> trackStates = new HashMap<>();
    private MediaPlayerService mediaPlayerService;
    private MediaPlayerTrack mediaPlayerTrack;
    private Context context;
    private int currentMediaID = Constants.RADIO_HITS_ID;

    public static MediaPlayerController getInstance() {
        if (mediaPlayerController == null) {
            mediaPlayerController = new MediaPlayerController();
        }
        return mediaPlayerController;
    }

    public void setMediaPlayerService(Context context, MediaPlayerService mediaPlayerService) {
        this.mediaPlayerService = mediaPlayerService;
        this.context = context;
    }

    public int getPlayingMediaID() {
        return currentMediaID;
    }

    public int getMediaState(int channelID) {
        return trackStates.get(channelID);
    }

    public void setMediaState(int mediaState) {
        this.mediaState = mediaState;
    }

    public void doMediaAction(MediaPlayerTrack mediaPlayerTrack) {
        this.mediaPlayerTrack = mediaPlayerTrack;

        configMediaPlayer();

        if (trackStates.containsKey(mediaPlayerTrack.getMediaChannelID()))
            mediaState = trackStates.get(mediaPlayerTrack.getMediaChannelID());

        switch (mediaState) {
            case STATE_PLAYING:
                if (mediaPlayer != null)
                    mediaPlayer.pause();

                mediaPlayerService.stopSelf();
                trackStates.put(mediaPlayerTrack.getMediaChannelID(), STATE_PAUSED);
                break;
            case STATE_PAUSED:
                currentMediaID = mediaPlayerTrack.getMediaChannelID();
                trackStates.put(mediaPlayerTrack.getMediaChannelID(), STATE_PLAYING);
                play();
                break;
        }
    }


    private void configMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            mediaPlayer.setDataSource(mediaPlayerTrack.getMediaTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.mediaPlayer.start();
    }

    private void play() {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.prepareAsync();
    }
}
