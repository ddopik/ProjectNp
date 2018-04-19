package com.spade.nrc.ui.news.view;

import android.widget.ProgressBar;

import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.ui.news.model.News;
import com.spade.nrc.ui.news.model.NewsInner;

import java.util.List;

public interface NewsDetailsView {


    void showProgressBar();

    void hideProgressBar();

    void viewRelatedNews(List<NewsInner> newsList);

    void viewNewsDetailsInnerImage(String imageUrl, RequestOptions requestOptions);
    void viewNewsDetailsInnerTitle(String title);
    void viewNewsDetailsInnerDate(String date);
    void viewNewsDetailsInnerDescription(String description);


    void hideRelatedNews();
    void showRelatedNewsLoading();
    void hideRelatedNewsLoading();

}