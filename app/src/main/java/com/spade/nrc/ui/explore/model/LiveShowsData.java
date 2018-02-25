package com.spade.nrc.ui.explore.model;

import com.google.gson.annotations.SerializedName;
import com.spade.nrc.ui.shows.model.Channel;
import com.spade.nrc.ui.shows.model.Show;

/**
 * Created by Ayman Abouzeid on 2/25/18.
 */

public class LiveShowsData {
    @SerializedName("show")
    private Show show;
    @SerializedName("channel")
    private Channel channel;

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
