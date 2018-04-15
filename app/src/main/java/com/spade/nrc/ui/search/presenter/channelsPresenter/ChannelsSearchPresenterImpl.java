package com.spade.nrc.ui.search.presenter.channelsPresenter;

import com.androidnetworking.error.ANError;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.search.view.channelsSearch.ChannelsSearchView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public class ChannelsSearchPresenterImpl implements ChannelsSearchPresenter {

    private ChannelsSearchView channelsSearchView;

    @Override
    public void findChannels(String key) {
        channelsSearchView.showLoading();
        ApiHelper.getSearchChannels(PrefUtils.getAppLang(NRCApplication.nrcApplication), key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(channelsResponse -> {
                    channelsSearchView.hideLoading();
                    channelsSearchView.viewChannelsList(channelsResponse.getData().getData());
                }, throwable -> {
                    channelsSearchView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        channelsSearchView.hideChannelsList();
//                        fragmentSearchChannelsView.viewStateMessage(anError.getErrorBody());
                    }
                });


    }

    @Override
    public void setView(ChannelsSearchView view) {
        this.channelsSearchView = view;
    }
}
