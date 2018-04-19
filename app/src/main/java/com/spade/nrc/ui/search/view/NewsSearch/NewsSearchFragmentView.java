package com.spade.nrc.ui.search.view.NewsSearch;

import com.spade.nrc.ui.news.model.News;
import com.spade.nrc.ui.shows.model.Channel;

import java.util.List;

public interface NewsSearchFragmentView  {

    void viewNewsList(List<News> newsList);

    void hideNewsList();

    void hideProgressBar();

    void showProgressBar();
}
