package com.spade.nrc.ui.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InnerNewsResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private NewsInner news;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public NewsInner getData() {
        return news;
    }

    public void setData(NewsInner news) {
        this.news = news;
    }

}
