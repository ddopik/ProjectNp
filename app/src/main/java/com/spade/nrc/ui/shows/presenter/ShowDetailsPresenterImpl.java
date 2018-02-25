package com.spade.nrc.ui.shows.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.shows.view.ShowDetailsView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 1/26/18.
 */

public class ShowDetailsPresenterImpl implements ShowDetailsPresenter {

    private ShowDetailsView showDetailsView;
    private Context context;

    public ShowDetailsPresenterImpl(Context context) {
        this.context = context;
    }


    @Override
    public void setView(ShowDetailsView view) {
        showDetailsView = view;
    }

    @Override
    public void getShowDetails(String showID) {
        showDetailsView.showLoading();
        ApiHelper.getShowDetails(PrefUtils.getAppLang(context), showID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showDetailsResponse -> {
                    showDetailsView.hideLoading();
                    if (showDetailsResponse.getShow() != null)
                        showDetailsView.displayShowData(showDetailsResponse.getShow());
                }, throwable -> {
                    showDetailsView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        showDetailsView.showMessage(anError.getMessage());
                    }
                });
    }
}
