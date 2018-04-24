package com.spade.nrc.ui.channel.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.androidnetworking.error.ANError;
import com.spade.nrc.R;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.realm.RealmDbHelper;
import com.spade.nrc.realm.RealmDbImpl;
import com.spade.nrc.ui.channel.view.AboutChannelFragment;
import com.spade.nrc.ui.channel.view.ChannelsDetailsView;
import com.spade.nrc.ui.channel.view.LiveStreamingFragment;
import com.spade.nrc.ui.channel.view.ScheduleFragment;
import com.spade.nrc.ui.presenters.view.PresentersFragment;
import com.spade.nrc.ui.shows.view.ShowsFragment;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.ErrorUtils;
import com.spade.nrc.utils.LoginProviders;
import com.spade.nrc.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 1/14/18.
 */

public class ChannelDetailsPresenterImpl implements ChannelDetailsPresenter {

    private ChannelsDetailsView channelsDetailsView;
    private Context context;
    private RealmDbHelper realmDbHelper;

    public ChannelDetailsPresenterImpl(Context context) {
        this.context = context;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(ChannelsDetailsView view) {
        channelsDetailsView = view;
    }

    @Override
    public void setUpViewPager(int channelID) {

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_CHANNEL_ID, channelID);
        bundle.putInt(Constants.EXTRA_SHOW_TYPE, Constants.NORMAL_SHOW_TYPE);

        LiveStreamingFragment liveStreamingFragment = new LiveStreamingFragment();
        liveStreamingFragment.setArguments(bundle);

        ShowsFragment showsFragment = new ShowsFragment();
        showsFragment.setArguments(bundle);

        PresentersFragment presentersFragment = new PresentersFragment();
        presentersFragment.setArguments(bundle);

        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.setArguments(bundle);

        AboutChannelFragment aboutChannelFragment = new AboutChannelFragment();
        aboutChannelFragment.setArguments(bundle);

        List<Fragment> fragments = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        fragments.add(liveStreamingFragment);
        fragments.add(scheduleFragment);
        fragments.add(showsFragment);
        fragments.add(presentersFragment);
        fragments.add(aboutChannelFragment);

        fragmentTitles.add(context.getString(R.string.live_streaming));
        fragmentTitles.add(context.getString(R.string.schedule));
        fragmentTitles.add(context.getString(R.string.shows));
        fragmentTitles.add(context.getString(R.string.presenters));
        fragmentTitles.add(context.getString(R.string.about));

        channelsDetailsView.addFragment(fragments, fragmentTitles);
    }

    @Override
    public void addChannelToFav(int channelID) {
        if (PrefUtils.getLoginProvider(context) != LoginProviders.NONE.getLoginProviderCode()) {
            channelsDetailsView.showLoading();
            ApiHelper.addToFavourite(String.valueOf(channelID), ApiHelper.ADD_CHANNEL_TO_FAV, PrefUtils.getUserToken(context), PrefUtils.getAppLang(context))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(addToFavouriteResponse -> {
                        realmDbHelper.updateChannelData(addToFavouriteResponse.getChannelRealm());
                        channelsDetailsView.hideLoading();
                        channelsDetailsView.updateAddToFavouriteBtn();
                    }, throwable -> {
                        channelsDetailsView.hideLoading();
                        if (throwable != null) {
                            ANError anError = (ANError) throwable;
                            channelsDetailsView.showMessage(ErrorUtils.getErrors(anError));
                        }
                    });
        }
    }

    @Override
    public boolean isLiked(int channelID) {
        return realmDbHelper.isChannelLiked(channelID);
    }
}
