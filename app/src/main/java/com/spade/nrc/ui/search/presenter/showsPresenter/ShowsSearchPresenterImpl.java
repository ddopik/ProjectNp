package com.spade.nrc.ui.search.presenter.showsPresenter;

import com.androidnetworking.error.ANError;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.search.view.ShowSearch.ShowsSearchView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public class ShowsSearchPresenterImpl implements ShowSearchPresenter {

    private ShowsSearchView showsSearchView;


    @Override
    public void findShows(String key) {
        showsSearchView.showLoading();
        ApiHelper.getSearchShows(PrefUtils.getAppLang(NRCApplication.nrcApplication), key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showsResponse -> {
                    showsSearchView.hideLoading();
                    showsSearchView.viewShowsList(showsResponse.getShowsData().getShows());
                }, throwable -> {
                    showsSearchView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        showsSearchView.hideShowsList();
//                        fragmentSearchShowView.viewStateMessage(anError.getResponse().toString());
                    }
                });


    }

    @Override
    public void setView(ShowsSearchView view) {
        this.showsSearchView = view;

    }
}
