package com.spade.nrc.ui.channel.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.realm.RealmDbHelper;
import com.spade.nrc.realm.RealmDbImpl;
import com.spade.nrc.ui.channel.view.LiveStreamingView;
import com.spade.nrc.utils.ErrorUtils;
import com.spade.nrc.utils.LoginProviders;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 1/31/18.
 */

public class LiveStreamingPresenterImpl implements LiveStreamingPresenter {

    private Context context;
    private LiveStreamingView liveStreamingView;
    private RealmDbHelper realmDbHelper;

    public LiveStreamingPresenterImpl(Context context) {
        this.context = context;
        this.realmDbHelper = new RealmDbImpl();
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
                    liveStreamingView.updateFavBtn();
                }, throwable -> {
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        liveStreamingView.showMessage(ErrorUtils.getErrors(anError));
                    }
                });
    }

    @Override
    public void addShowToFav(int showID) {
        if (PrefUtils.getLoginProvider(context) != LoginProviders.NONE.getLoginProviderCode()) {
            liveStreamingView.showLoading();
            ApiHelper.addShowToFavourite(String.valueOf(showID), ApiHelper.ADD_SHOW_TO_FAV, PrefUtils.getUserToken(context), PrefUtils.getAppLang(context))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(addToFavouriteResponse -> {
                        realmDbHelper.updateShowData(addToFavouriteResponse.getShowRealm());
                        liveStreamingView.hideLoading();
                        liveStreamingView.updateFavBtn();
                    }, throwable -> {
                        liveStreamingView.hideLoading();
                        if (throwable != null) {
                            ANError anError = (ANError) throwable;
                            liveStreamingView.showMessage(ErrorUtils.getErrors(anError));
                        }
                    });
        }
    }

    @Override
    public boolean isLiked(int showID) {
        return realmDbHelper.isShowLiked(showID);
    }
}
