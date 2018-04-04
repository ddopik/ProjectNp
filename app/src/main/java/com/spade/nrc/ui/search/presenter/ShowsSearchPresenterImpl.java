package com.spade.nrc.ui.search.presenter;

import com.androidnetworking.error.ANError;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.search.view.FragmentSearchShowView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public class ShowsSearchPresenterImpl implements ShowSearchPresenter {


    FragmentSearchShowView fragmentSearchShowView;
    private String type="shows";


    public ShowsSearchPresenterImpl(FragmentSearchShowView fragmentSearchShowView) {

        this.fragmentSearchShowView = fragmentSearchShowView;
    }

    @Override
    public void findShows(String key) {
        fragmentSearchShowView.showProgressBar();
        ApiHelper.getShows(PrefUtils.getAppLang(NRCApplication.nrcApplication), type, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showsResponse -> {
                    fragmentSearchShowView.hideProgressBar();
                    fragmentSearchShowView.viewShowsList(showsResponse.getShowsData().getShows());
                }, throwable -> {
                    fragmentSearchShowView.hideProgressBar();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        fragmentSearchShowView.hideShowsList();
                        fragmentSearchShowView.viewStateMessage(anError.getMessage());
                    }
                });


    }
}
