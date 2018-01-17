package com.spade.nrc.ui.explore.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 1/8/18.
 */

public class SlideBanner {

    @SerializedName("media")
    private String slideImage;

    public String getSlideImage() {
        return slideImage;
    }

    public void setSlideImage(String slideImage) {
        this.slideImage = slideImage;
    }
}
