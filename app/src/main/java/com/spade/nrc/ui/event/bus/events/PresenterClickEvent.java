package com.spade.nrc.ui.event.bus.events;

/**
 * Created by Ayman Abouzeid on 1/27/18.
 */

public class PresenterClickEvent {
    private int presenterID;
    private int channelID;
    private boolean navigate;

    public PresenterClickEvent(int presenterID, int channelID, boolean navigate) {
        this.presenterID = presenterID;
        this.channelID = channelID;
        this.navigate = navigate;
    }

    public int getPresenterID() {
        return presenterID;
    }

    public void setPresenterID(int presenterID) {
        this.presenterID = presenterID;
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
