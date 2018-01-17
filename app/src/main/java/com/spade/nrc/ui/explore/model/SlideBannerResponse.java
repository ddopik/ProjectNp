package com.spade.nrc.ui.explore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/8/18.
 */

public class SlideBannerResponse {

    @SerializedName("data")
    private List<SlideBanner> slideBannerList;

    public List<SlideBanner> getSlideBannerList() {
        return slideBannerList;
    }

    public void setSlideBannerList(List<SlideBanner> slideBannerList) {
        this.slideBannerList = slideBannerList;
    }
}
