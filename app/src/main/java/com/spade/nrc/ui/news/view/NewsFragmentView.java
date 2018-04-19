package com.spade.nrc.ui.news.view;

import com.spade.nrc.ui.news.model.News;

import java.util.List;

public interface NewsFragmentView {

    void viewNews(List<News> newsList);

    void hideNews();

    void showProgressBar();

    void hideProgressBar();

    void viewStateMessage(String msg);
}
