package com.spade.nrc.ui.ads;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 4/11/18.
 */

public class AdsResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("data")
    private List<AdModel> adModelList;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<AdModel> getAdModelList() {
        return adModelList;
    }

    public void setAdModelList(List<AdModel> adModelList) {
        this.adModelList = adModelList;
    }
}
