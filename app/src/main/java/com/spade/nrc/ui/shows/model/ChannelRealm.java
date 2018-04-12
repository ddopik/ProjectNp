package com.spade.nrc.ui.shows.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ayman Abouzeid on 1/11/18.
 */

public class ChannelRealm extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    private Integer id;
    @SerializedName("is_liked")
    private boolean isLiked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
