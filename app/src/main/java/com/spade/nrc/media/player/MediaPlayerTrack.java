package com.spade.nrc.media.player;

import com.spade.nrc.ui.shows.model.Schedule;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/20/18.
 */

public class MediaPlayerTrack {

    private int mediaChannelID;
    private String mediaTitle;
    private List<Schedule> schedules;

    public MediaPlayerTrack(int mediaChannelID, String mediaTitle, List<Schedule> schedules) {
        this.mediaChannelID = mediaChannelID;
        this.mediaTitle = mediaTitle;
        this.schedules = schedules;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
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
