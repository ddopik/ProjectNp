package com.spade.nrc.ui.search.presenter.channelsPresenter;

import com.androidnetworking.error.ANError;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.search.view.channelsSearch.FragmentSearchChannelsView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public class ChannelsSearchPresenterImpl implements ChannelsSearchPresenter {
    FragmentSearchChannelsView fragmentSearchChannelsView;



    public ChannelsSearchPresenterImpl(FragmentSearchChannelsView fragmentSearchChannelsView) {

        this.fragmentSearchChannelsView = fragmentSearchChannelsView;
    }

    @Override
    public void findChannels(String key) {
        fragmentSearchChannelsView.showProgressBar();
        ApiHelper.getSearchChannels(PrefUtils.getAppLang(NRCApplication.nrcApplication), key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(channelsResponse -> {
                    fragmentSearchChannelsView.hideProgressBar();
                    fragmentSearchChannelsView.viewChannelsList(channelsResponse.getData());
                }, throwable -> {
                    fragmentSearchChannelsView.hideProgressBar();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        fragmentSearchChannelsView.hideChannelsList();
                        fragmentSearchChannelsView.viewStateMessage(anError.getErrorBody());
                    }
                });


    }
}
