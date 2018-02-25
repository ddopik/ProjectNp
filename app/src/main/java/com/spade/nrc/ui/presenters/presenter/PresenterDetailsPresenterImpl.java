package com.spade.nrc.ui.presenters.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.presenters.view.PresenterDetailsView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 1/26/18.
 */

public class PresenterDetailsPresenterImpl implements PresenterDetailsPresenter {

    private PresenterDetailsView presenterDetailsView;
    private Context context;

    public PresenterDetailsPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setView(PresenterDetailsView view) {
        presenterDetailsView = view;
    }

    @Override
    public void getPresenterDetails(String presenterID, String channelID) {
        presenterDetailsView.showLoading();
        ApiHelper.getPresenterDetails(PrefUtils.getAppLang(context), presenterID, channelID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(presenterDetailsResponse -> {
                    presenterDetailsView.hideLoading();
                    if (presenterDetailsResponse.getPresenter() != null)
                        presenterDetailsView.showPresenterData(presenterDetailsResponse.getPresenter());
                }, throwable -> {
                    presenterDetailsView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        presenterDetailsView.showMessage(anError.getMessage());
                    }
                });
    }
}
