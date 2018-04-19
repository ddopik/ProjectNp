package com.spade.nrc.ui.news.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.news.view.NewsFragmentView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsPresenterImp implements NewsPresenter {

    private  NewsFragmentView newsFragmentView;
    private Context context;


    public NewsPresenterImp (Context context){
        this.context=context;
    }
    @Override
    public void getNews() {

        newsFragmentView.showProgressBar();
        ApiHelper.getNews(PrefUtils.getAppLang(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsResponse -> {
                    newsFragmentView.hideProgressBar();
                    newsFragmentView.viewNews(newsResponse.getData());
                }, throwable -> {
                    newsFragmentView.hideProgressBar();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        newsFragmentView.viewStateMessage(anError.getMessage());
                    }
                });

    }

    @Override
    public void setView(NewsFragmentView view) {
        newsFragmentView=view;
    }


}
