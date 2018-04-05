package com.spade.nrc.ui.search.presenter.presentersPresenter;

import com.androidnetworking.error.ANError;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.search.view.presentersSearch.FragmentSearchPresentersView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public class PresentersSearchPresenterImpl implements PresentersSearchPresenter {


    FragmentSearchPresentersView fragmentSearchPresentersView;

    public PresentersSearchPresenterImpl(FragmentSearchPresentersView fragmentSearchPresentersView) {
        this.fragmentSearchPresentersView=fragmentSearchPresentersView;
    }

    @Override
    public void findPresenter(String key) {
        fragmentSearchPresentersView.showProgressBar();
        ApiHelper.getSearchPresenters(PrefUtils.getAppLang(NRCApplication.nrcApplication), key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showsResponse -> {
                    fragmentSearchPresentersView.hideProgressBar();
                    fragmentSearchPresentersView.viewPresentersList(showsResponse.getPresenterData().getPresenters());
                }, throwable -> {
                    fragmentSearchPresentersView.hideProgressBar();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        fragmentSearchPresentersView.hidePresentersList();
                        fragmentSearchPresentersView.viewStateMessage(anError.getResponse().toString());
                    }
                });


    }
}
