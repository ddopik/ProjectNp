package com.spade.nrc.ui.shows.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by Ayman Abouzeid on 1/11/18.
 */

public class AddShowToFavouriteResponse extends RealmObject {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private ShowRealm showRealm = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ShowRealm getShowRealm() {
        return showRealm;
    }

    public void setData(ShowRealm channelRealm) {
        this.showRealm = channelRealm;
    }

}
