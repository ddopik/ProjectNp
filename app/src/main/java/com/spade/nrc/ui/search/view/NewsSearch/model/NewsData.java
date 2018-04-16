package com.spade.nrc.ui.search.view.NewsSearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.nrc.ui.news.model.News;

import java.util.List;

public class NewsData {

    @SerializedName("news")
    @Expose
    private List<News> news = null;

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

}
