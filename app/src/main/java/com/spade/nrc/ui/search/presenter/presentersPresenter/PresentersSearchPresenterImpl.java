package com.spade.nrc.ui.search.presenter.presentersPresenter;

import com.androidnetworking.error.ANError;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.search.view.presentersSearch.PresentersSearchView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public class PresentersSearchPresenterImpl implements PresentersSearchPresenter {

    private PresentersSearchView presentersSearchView;

    @Override
    public void findPresenter(String key) {
        presentersSearchView.showLoading();
        ApiHelper.getSearchPresenters(PrefUtils.getAppLang(NRCApplication.nrcApplication), key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showsResponse -> {
                    presentersSearchView.hideLoading();
                    presentersSearchView.viewPresentersList(showsResponse.getPresenterData().getPresenters());
                }, throwable -> {
                    presentersSearchView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        presentersSearchView.hidePresentersList();
//                        fragmentSearchPresentersView.viewStateMessage(anError.getResponse().toString());
                    }
                });


    }

    @Override
    public void setView(PresentersSearchView view) {
        presentersSearchView = view;
    }
}
