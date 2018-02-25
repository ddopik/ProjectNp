package com.spade.nrc.ui.event.bus.events;

/**
 * Created by Ayman Abouzeid on 1/27/18.
 */

public class ShowsClickEvent {
    private int showID;
    private int channelID;
    private boolean navigate;

    public ShowsClickEvent(int showID, int channelID, boolean navigate) {
        this.showID = showID;
        this.channelID = channelID;
        this.navigate = navigate;
    }

    public int getShowID() {
        return showID;
    }

    public void setShowID(int showID) {
        this.showID = showID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public boolean isNavigate() {
        return navigate;
    }

    public void setNavigate(boolean navigate) {
        this.navigate = navigate;
    }
}
