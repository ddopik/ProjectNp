package com.spade.nrc.ui.search.presenter.NewsPresenter;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.error.ANError;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.search.view.NewsSearch.NewsSearchFragmentView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsSearchPresenterImp implements NewsSearchPresenter {

    private Context context;
    private NewsSearchFragmentView newsSearchFragmentView;

    public NewsSearchPresenterImp(Context context) {
        this.context = context;

    }

    @Override
    public void setView(NewsSearchFragmentView view) {
        this.newsSearchFragmentView = view;

    }

    @Override
    public void findNews(String key) {
        ApiHelper.getSearchNews(PrefUtils.getAppLang(NRCApplication.nrcApplication), key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsResponse -> {
                    newsSearchFragmentView.hideProgressBar();
                    newsSearchFragmentView.viewNewsList(newsResponse.getData().getNews());
                }, throwable -> {
                    newsSearchFragmentView.hideProgressBar();
                    if (throwable != null) {
                        newsSearchFragmentView.hideNewsList();
                        Log.e("NewsSearchPresenterImp","Error--->"+throwable.getMessage());
                    }
                });

    }
}