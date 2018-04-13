package com.spade.nrc.ui.profile.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.ui.profile.view.UserFavouriteShowsView;
import com.spade.nrc.utils.ErrorUtils;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 4/13/18.
 */

public class UserFavouriteShowsPresenterImpl implements UserFavouriteShowsPresenter {

    private Context context;
    private UserFavouriteShowsView userFavouriteShowsView;

    public UserFavouriteShowsPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setView(UserFavouriteShowsView view) {
        userFavouriteShowsView = view;
    }

    @Override
    public void getUSerShows() {
        userFavouriteShowsView.showLoading();
        ApiHelper.getUserShows(PrefUtils.getAppLang(context), PrefUtils.getUserToken(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showsResponse -> {
                    userFavouriteShowsView.hideLoading();
                    userFavouriteShowsView.showShows(showsResponse.getData());
                }, throwable -> {
                    userFavouriteShowsView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        userFavouriteShowsView.showMessage(ErrorUtils.getErrors(anError));
                    }
                });
    }
}
