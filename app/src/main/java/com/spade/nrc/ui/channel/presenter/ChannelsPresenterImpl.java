package com.spade.nrc.ui.channel.presenter;

import android.content.Context;

import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.channel.view.ChannelsView;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 1/31/18.
 */

public class ChannelsPresenterImpl implements ChannelsPresenter {

    private Context context;
    private ChannelsView channelsView;
    private ChannelUtils channelUtils;

    public ChannelsPresenterImpl(Context context) {
        this.context = context;
        channelUtils = ChannelUtils.getInstance();
    }

    @Override
    public void setView(ChannelsView view) {
        channelsView = view;
    }

    @Override
    public void getChannels() {
        channelsView.showLoading();
        ApiHelper.getChannels(PrefUtils.getAppLang(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(channelsResponse -> {
                    channelsView.hideLoading();
                    channelUtils.setChannelList(channelsResponse.getData());
                    channelsView.channelsLoaded();
                }, throwable -> {
                    channelsView.showMessage("Something wrong happened");
                });
    }
}
