package com.spade.nrc.ui.channel.presenter;

import com.spade.nrc.ui.channel.view.AboutChannelView;
import com.spade.nrc.utils.ChannelUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 2/5/18.
 */

public class AboutChannelPresenterImpl implements AboutChannelPresenter {

    private AboutChannelView aboutChannelView;

    @Override
    public void getChannelInfo(int channelID) {
        ChannelUtils channelUtils = ChannelUtils.getInstance();
        channelUtils.getChannelById(channelID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(channel -> aboutChannelView.showChannelData(channel));
    }

    @Override
    public void setView(AboutChannelView view) {
        aboutChannelView = view;
    }
}
