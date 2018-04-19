package com.spade.nrc.ui.news.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RelatedNewsResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<NewsInner> newsList = new ArrayList<>();

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<NewsInner> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsInner> data) {
        this.newsList = data;
    }

}
