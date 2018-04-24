package com.spade.nrc.ui.player;

import android.content.Context;
import android.os.Bundle;

import com.androidnetworking.error.ANError;
import com.spade.nrc.R;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.channel.view.ChannelDetailsFragment;
import com.spade.nrc.ui.general.NavigationManager;
import com.spade.nrc.ui.main.MainActivity;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 1/3/18.
 */

public class PlayerPresenterImpl implements PlayerPresenter {

    private PlayerView playerView;
    private Context context;
    private NavigationManager navigationManager;

    public PlayerPresenterImpl(Context context) {
        this.context = context;
        this.navigationManager = new NavigationManager((MainActivity) context);
    }

    @Override
    public void setView(PlayerView view) {
        this.playerView = view;
    }

//    @Override
//    public void getLiveNowShows() {
//        playerView.showLoading();
//        ApiHelper.getLiveShows(PrefUtils.getAppLang(context))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(showsResponse -> {
//                    playerView.hideLoading();
//                    playerView.showLiveNowShows(showsResponse.getData());
//                    MusicProvider musicProvider = MusicProvider.getInstance();
//                    musicProvider.setShowsList(showsResponse.getData());
//                }, throwable -> {
//                    playerView.hideLoading();
//                    if (throwable != null) {
//                        ANError anError = (ANError) throwable;
//                        playerView.showMessage(anError.getMessage());
//                    }
//                });
//    }

    @Override
    public void getLiveStreamingShows(String channelID) {
        playerView.showLoading();
        ApiHelper.getCurrentAndNextShows(PrefUtils.getAppLang(context), channelID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currentAndNextShowsResponse -> {
                    playerView.hideLoading();
                    if (currentAndNextShowsResponse.getCurrentAndNextShowsData() != null) {
                        playerView.showCurrentShow(currentAndNextShowsResponse.getCurrentAndNextShowsData().getCurrentShow());
                        playerView.showNextShow(currentAndNextShowsResponse.getCurrentAndNextShowsData().getNextShow());
                    } else {
                        playerView.showCurrentShow(null);
                        playerView.showNextShow(null);
                    }

                }, throwable -> {
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                    }
                });
    }


//
//    @Override
//    public void getShowByChannelID(int channelID, List<Show> showList) {
//        getShowByChannel(channelID, showList)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(show -> {
//                    playerView.updateUI(show);
//                });
//    }
//
//    public Observable<Show> getShowByChannel(int channelID, List<Show> showList) {
//        return Observable.create(e -> {
//            for (Show show : showList) {
//                if (show.getChannel().getId() == channelID) {
//                    e.onNext(show);
//                    e.onComplete();
//                }
//            }
//        });
//    }

}
