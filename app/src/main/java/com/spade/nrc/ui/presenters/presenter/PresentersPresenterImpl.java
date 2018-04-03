package com.spade.nrc.ui.presenters.presenter;

import android.content.Context;

import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.presenters.view.PresentersView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public class PresentersPresenterImpl implements PresentersPresenter {
    private Context context;
    private PresentersView presentersView;

    public PresentersPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setView(PresentersView view) {
        this.presentersView = view;
    }

    @Override
    public void getPresenters(String appLang, int channelID, int pageNumber) {
        presentersView.showLoading();
        ApiHelper.getPresenters(appLang, String.valueOf(channelID), String.valueOf(pageNumber))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(presentersResponse -> {
                    presentersView.showPresenters(presentersResponse.getPresenterData());
                    presentersView.hideLoading();
                }, throwable -> {
                    presentersView.hideLoading();
                });
    }
}
