package com.spade.nrc.ui.explore.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.R;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.nrc.media.player.MusicProvider;
import com.spade.nrc.realm.RealmDbImpl;
import com.spade.nrc.ui.explore.view.ExploreView;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 1/3/18.
 */

public class ExplorePresenterImpl implements ExplorePresenter {

    private ExploreView exploreView;
    private Context context;
    private RealmDbImpl realmDb;
    private RequestOptions requestOptions;

    public ExplorePresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setView(ExploreView view) {
        this.exploreView = view;
    }

    @Override
    public void getFeaturedShows() {
        exploreView.showFeaturedShowsProgress();
        ApiHelper.getFeaturedShows(PrefUtils.getAppLang(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showsResponse -> {
                    exploreView.hideFeaturedShowsProgress();
                    exploreView.showFeaturedShows(showsResponse.getData());
                }, throwable -> {
                    exploreView.hideFeaturedShowsProgress();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        exploreView.showMessage(anError.getMessage());
                    }
                });
    }

    @Override
    public void getLiveNowShows() {
        exploreView.showLiveShowsProgress();
        ApiHelper.getLiveShows(PrefUtils.getAppLang(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(showsResponse -> {
                    exploreView.hideLiveShowsProgress();
                    exploreView.showLiveNowShows(showsResponse.getData());
//                    MusicProvider musicProvider = MusicProvider.getInstance();
//                    musicProvider.setShowsList(showsResponse.getData());
                }, throwable -> {
                    exploreView.hideLiveShowsProgress();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        exploreView.showMessage(anError.getMessage());
                    }
                });
    }

    @Override
    public void getSlidingBanners() {
        exploreView.showSliderProgress();
        ApiHelper.getSlidingBanners(PrefUtils.getAppLang(context))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(slideBannerResponse -> {
                    exploreView.hideSliderProgress();
                    exploreView.showSlides(slideBannerResponse.getSlideBannerList());
                }, throwable -> {
                    exploreView.hideSliderProgress();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        exploreView.showMessage(anError.getErrorDetail());
                    }
                });
    }

    @Override
    public void getProfilePic() {
        realmDb = new RealmDbImpl();
        requestOptions = new RequestOptions();
        if (PrefUtils.isLoggedIn(context)) {
            exploreView.showProfilePic(realmDb.getUser(PrefUtils.getUserId(context)).getUserPhoto(), requestOptions);
        } else {
            exploreView.hideProfilePic();
        }
    }
}
