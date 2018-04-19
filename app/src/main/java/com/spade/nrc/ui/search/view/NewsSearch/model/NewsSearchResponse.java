package com.spade.nrc.ui.search.view.NewsSearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.nrc.ui.news.model.News;

import java.util.List;

public class NewsSearchResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private NewsData data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public NewsData getData() {
        return data;
    }

    public void setData(NewsData data) {
        this.data = data;
    }
}
