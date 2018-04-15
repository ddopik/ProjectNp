package com.spade.nrc.ui.profile.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.profile.view.UserFavouriteChannelsView;
import com.spade.nrc.utils.ErrorUtils;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 4/13/18.
 */

public class UserFavouriteChannelsPresenterImpl implements UserFavouriteChannelsPresenter {

    private Context context;
    private UserFavouriteChannelsView userFavouriteChannelsView;

    public UserFavouriteChannelsPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setView(UserFavouriteChannelsView view) {
        userFavouriteChannelsView = view;
    }

    @Override
    public void getUSerChannels() {
        userFavouriteChannelsView.showLoading();
        ApiHelper.getUserChannels(PrefUtils.getAppLang(context), PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(channelsResponse -> {
                    userFavouriteChannelsView.hideLoading();
                    userFavouriteChannelsView.showChannels(channelsResponse.getData());
                }, throwable -> {
                    userFavouriteChannelsView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        userFavouriteChannelsView.showMessage(ErrorUtils.getErrors(anError));
                    }
                });
    }
}
