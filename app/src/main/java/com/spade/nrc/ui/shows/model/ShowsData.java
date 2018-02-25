package com.spade.nrc.ui.shows.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/18/18.
 */

public class ShowsData {

    @SerializedName("shows")
    @Expose
    private List<Show> shows = new ArrayList<>();

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
