package com.spade.nrc.ui.news.presenter;

import android.view.View;

import com.spade.nrc.ui.news.model.News;
import com.spade.nrc.ui.news.view.NewsFragmentView;

import java.util.List;

public interface NewsPresenter {

    void getNews();

    void setView(NewsFragmentView view);
}
