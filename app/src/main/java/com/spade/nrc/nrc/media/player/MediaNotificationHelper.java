/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spade.nrc.nrc.media.player;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.spade.nrc.R;
import com.spade.nrc.utils.ChannelUtils;

/**
 * Helper class for building Media style Notifications from a
 * {@link android.support.v4.media.session.MediaSessionCompat}.
 */
public class MediaNotificationHelper extends ContextWrapper {

    private static NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    public String CHANNEL_ONE_ID = getPackageName();
    private int notificationID = 101;

    private CharSequence CHANNEL_ONE_NAME = "NRC Live Streaming";

    public MediaNotificationHelper(Context context) {
        super(context);
        createChannels();
    }

    public void notify(Notification notification) {
        getManager().notify(notificationID, notification);
    }


    private NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public void createChannels() {
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(false);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(notificationChannel);
        }
    }


    public static Notification createNotification(Context context,
                                                  MediaSessionCompat mediaSession, String channelID) {
        MediaControllerCompat controller = mediaSession.getController();
        MediaMetadataCompat mMetadata = controller.getMetadata();
        PlaybackStateCompat mPlaybackState = controller.getPlaybackState();

        if (mMetadata == null || mPlaybackState == null) {
            return null;
        }

        boolean isPlaying = mPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING;
        NotificationCompat.Action action = isPlaying
                ? new NotificationCompat.Action(R.drawable.ic_pause_white_24dp,
                context.getString(R.string.player),
                MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                        PlaybackStateCompat.ACTION_PAUSE))
                : new NotificationCompat.Action(R.drawable.ic_play_arrow_white_24dp,
                context.getString(R.string.player),
                MediaButtonReceiver.buildMediaButtonPendingIntent(context,
                        PlaybackStateCompat.ACTION_PLAY));

//        MediaDescriptionCompat description = mMetadata.getDescription();
        String title = String.format(context.getString(R.string.enjoy_listening), context.getString(
                ChannelUtils.getChannelTitle(Integer.parseInt(mMetadata.getDescription().getMediaId()))));
        int albumArt = ChannelUtils.getLiveStreamingDefault(Integer.parseInt(mMetadata.getDescription().getMediaId()));
        Bitmap art = BitmapFactory.decodeResource(context.getResources(),
                albumArt);
//        if (notificationBuilder == null)
            notificationBuilder = new NotificationCompat.Builder(context, channelID);
//        else
//            notificationBuilder.mActions.clear();

        notificationBuilder
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(new int[]{0})
                        .setMediaSession(mediaSession.getSessionToken()))
                .addAction(action)
                .setSmallIcon(R.drawable.ic_nrclogowhite)
                .setLargeIcon(art)
                .setColorized(true)
                .setShowWhen(true)
                .setContentIntent(controller.getSessionActivity())
                .setContentText(title)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        return notificationBuilder.build();
    }
}
