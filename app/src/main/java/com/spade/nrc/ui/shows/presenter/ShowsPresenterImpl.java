package com.spade.nrc.ui.shows.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.shows.view.ShowsView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public class ShowsPresenterImpl implements ShowsPresenter {
    private Context context;
    private ShowsView showsView;

    public ShowsPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void getShows(String appLang, int channelID, int pageNumber) {
        showsView.showProgress();
        ApiHelper.getShows(PrefUtils.getAppLang(context), String.valueOf(channelID), String.valueOf(pageNumber))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showsResponse -> {
                    showsView.hideProgress();
                    showsView.displayShows(showsResponse.getShowsData());
                }, throwable -> {
                    showsView.hideProgress();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        showsView.showMessage(anError.getMessage());
                    }
                });
    }

    @Override
    public void getShowsByDay(String appLang, String day, int channelID, int pageNumber) {
        showsView.showProgress();
        ApiHelper.getShowsByDay(PrefUtils.getAppLang(context), day, String.valueOf(channelID), String.valueOf(pageNumber))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showsResponse -> {
                    showsView.hideProgress();
                    showsView.displayShows(showsResponse.getData());
                }, throwable -> {
                    showsView.hideProgress();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        showsView.showMessage(anError.getMessage());
                    }
                });
    }

    @Override
    public void setView(ShowsView view) {
        showsView = view;
    }
}
