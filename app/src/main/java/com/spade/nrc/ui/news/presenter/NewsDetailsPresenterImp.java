package com.spade.nrc.ui.news.presenter;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.news.model.News;
import com.spade.nrc.ui.news.model.NewsInner;
import com.spade.nrc.ui.news.model.RelatedNewsResponse;
import com.spade.nrc.ui.news.view.NewsDetailsView;
import com.spade.nrc.ui.shows.model.Schedule;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsPresenterImp implements NewsDetailsPresenter {
    private RequestOptions requestOptions;
    private NewsDetailsView newsDetailsView;

    private Context context;


    public NewsDetailsPresenterImp(NewsDetailsView newsDetailsView, Context context) {
        this.requestOptions = new RequestOptions();
        this.newsDetailsView=newsDetailsView;
        this.context = context;
    }

    @Override
    public void getNewsDetail(String ID) {
        ApiHelper.getInnerNews(ID, PrefUtils.getAppLang(context)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(innerNewsResponse -> {
                    newsDetailsView.hideProgressBar();
                    if (innerNewsResponse.getData() != null) {
                        NewsInner news = innerNewsResponse.getData();
                        if (news.getMedia() != null)
                            newsDetailsView.viewNewsDetailsInnerImage(news.getMedia(), requestOptions);
                        if (news.getTitle() != null)
                            newsDetailsView.viewNewsDetailsInnerTitle(news.getTitle());
                        if(news.getCreatedAt() !=null)
                            newsDetailsView.viewNewsDetailsInnerDate(news.getCreatedAt());
                        if (news.getDescription() != null)
                            newsDetailsView.viewNewsDetailsInnerDescription(news.getDescription());

                    }

                }, throwable -> {
                    newsDetailsView.hideProgressBar();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        Log.e("NewsDetailsPresenterImp", "Error---->" + anError.getErrorBody());
                    }
                });
    }

    @Override
    public void getRelatedNews(String newsID) {
        ApiHelper.getRelatedNews(PrefUtils.getAppLang(context),newsID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(relatedNewsResponse ->{
                    newsDetailsView.hideRelatedNewsLoading();
                    if(relatedNewsResponse.getNewsList().size() >0){
                        newsDetailsView.viewRelatedNews(relatedNewsResponse.getNewsList());
                    }

                },throwable -> {
                    newsDetailsView.hideRelatedNewsLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        Log.e("NewsDetailsPresenterImp", "Error---->" + anError.getErrorBody());
        }});

                }
}


