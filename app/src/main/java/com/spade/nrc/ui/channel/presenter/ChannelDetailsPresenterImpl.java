package com.spade.nrc.ui.channel.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.spade.nrc.R;
import com.spade.nrc.ui.channel.view.AboutChannelFragment;
import com.spade.nrc.ui.channel.view.ChannelsDetailsView;
import com.spade.nrc.ui.channel.view.LiveStreamingFragment;
import com.spade.nrc.ui.channel.view.ScheduleFragment;
import com.spade.nrc.ui.presenters.view.PresentersFragment;
import com.spade.nrc.ui.shows.view.ShowsFragment;
import com.spade.nrc.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/14/18.
 */

public class ChannelDetailsPresenterImpl implements ChannelDetailsPresenter {

    private ChannelsDetailsView channelsDetailsView;
    private Context context;

    public ChannelDetailsPresenterImpl(Context context) {
        this.context = context;
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
        fragments.add(showsFragment);
        fragments.add(presentersFragment);
        fragments.add(scheduleFragment);
        fragments.add(aboutChannelFragment);

        fragmentTitles.add(context.getString(R.string.live_streaming));
        fragmentTitles.add(context.getString(R.string.shows));
        fragmentTitles.add(context.getString(R.string.presenters));
        fragmentTitles.add(context.getString(R.string.schedule));
        fragmentTitles.add(context.getString(R.string.about));

        channelsDetailsView.addFragment(fragments, fragmentTitles);
    }
}
