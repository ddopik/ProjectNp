package com.spade.nrc.ui.channel.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.channel.view.LiveStreamingView;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 1/31/18.
 */

public class LiveStreamingPresenterImpl implements LiveStreamingPresenter {

    private Context context;
    private LiveStreamingView liveStreamingView;

    public LiveStreamingPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setView(LiveStreamingView view) {
        liveStreamingView = view;
    }

    @Override
    public void getLiveStreamingShows(String channelID) {
        liveStreamingView.showLoading();
        ApiHelper.getCurrentAndNextShows(PrefUtils.getAppLang(context), channelID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currentAndNextShowsResponse -> {
                    liveStreamingView.hideLoading();
                    if (currentAndNextShowsResponse.getCurrentAndNextShowsData() != null) {
                        liveStreamingView.showCurrentShow(currentAndNextShowsResponse.getCurrentAndNextShowsData().getCurrentShow());
                        liveStreamingView.showNextShow(currentAndNextShowsResponse.getCurrentAndNextShowsData().getNextShow());
                    } else {
                        liveStreamingView.showCurrentShow(null);
                        liveStreamingView.showNextShow(null);
                    }

                }, throwable -> {
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                    }
                });
    }
}
