package com.spade.nrc.media.player;

/**
 * Created by Ayman Abouzeid on 1/20/18.
 */

public class MediaPlayerTrack {

    private int mediaChannelID;
    private String mediaTitle;

    public MediaPlayerTrack(int mediaChannelID, String mediaTitle) {
        this.mediaChannelID = mediaChannelID;
        this.mediaTitle = mediaTitle;
    }

    public int getMediaChannelID() {
        return mediaChannelID;
    }

    public void setMediaChannelID(int mediaChannelID) {
        this.mediaChannelID = mediaChannelID;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }
}
