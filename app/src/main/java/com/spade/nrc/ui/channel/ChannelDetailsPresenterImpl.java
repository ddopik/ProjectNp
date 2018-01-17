package com.spade.nrc.ui.channel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.spade.nrc.R;
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

        ShowsFragment showsFragment = new ShowsFragment();
        showsFragment.setArguments(bundle);

        PresentersFragment presentersFragment = new PresentersFragment();
        presentersFragment.setArguments(bundle);

        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.setArguments(bundle);

        List<Fragment> fragments = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        fragments.add(showsFragment);
        fragments.add(presentersFragment);
        fragments.add(scheduleFragment);

        fragmentTitles.add(context.getString(R.string.shows));
        fragmentTitles.add(context.getString(R.string.presenters));
        fragmentTitles.add(context.getString(R.string.schedule));
        channelsDetailsView.addFragment(fragments, fragmentTitles);
    }
}
