package com.spade.nrc.ui.channel;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.spade.nrc.R;
import com.spade.nrc.ui.shows.view.ShowsFragment;
import com.spade.nrc.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/14/18.
 */

public class SchedulePresenterImpl implements SchedulePresenter {

    private ScheduleView scheduleView;
    private Context context;

    public SchedulePresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setUpViewPager(int channelID) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();
        String[] weekDaysText = context.getResources().getStringArray(R.array.week_days);

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_CHANNEL_ID, channelID);
        bundle.putInt(Constants.EXTRA_SHOW_TYPE, Constants.SCHEDULE_SHOW_TYPE);

        for (int i = 0; i < weekDaysText.length; i++) {
            bundle.putString(Constants.EXTRA_DAY, Constants.WEEK_DAYS[i]);
            ShowsFragment showsFragment = new ShowsFragment();
            showsFragment.setArguments(bundle);
            fragments.add(showsFragment);
            fragmentTitles.add(weekDaysText[i]);
        }
        scheduleView.addFragment(fragments, fragmentTitles);
    }

    @Override
    public void setView(ScheduleView view) {
        scheduleView = view;
    }
}
