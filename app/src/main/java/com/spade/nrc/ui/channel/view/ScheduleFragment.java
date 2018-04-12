package com.spade.nrc.ui.channel.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.channel.presenter.SchedulePresenter;
import com.spade.nrc.ui.channel.presenter.SchedulePresenterImpl;
import com.spade.nrc.ui.general.PagingAdapter;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/14/18.
 */

public class ScheduleFragment extends BaseFragment implements ScheduleView {

    private SchedulePresenter schedulePresenter;
    private View scheduleView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private PagingAdapter pagingAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        scheduleView = inflater.inflate(R.layout.fragment_schedule, container, false);
        initViews();
        return scheduleView;
    }

    @Override
    protected void initPresenter() {
        schedulePresenter = new SchedulePresenterImpl(getContext());
        schedulePresenter.setView(this);
    }

    @Override
    protected void initViews() {
        int channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        viewPager = scheduleView.findViewById(R.id.fragments_viewpager);
        tabLayout = scheduleView.findViewById(R.id.tabs);
        pagingAdapter = new PagingAdapter(getChildFragmentManager());
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(),
                ChannelUtils.getChannelSchedulesColorList(channelID)));
        schedulePresenter.setUpViewPager(channelID);
        sendAnalytics(String.format(getString(R.string.channel_schedule_analytics), getString(ChannelUtils.getChannelTitle(channelID))));
    }

    private void sendAnalytics(String screenName) {
        Tracker causesTracker = NRCApplication.getDefaultTracker();
        causesTracker.setScreenName(screenName);
        causesTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void showMessage(String message) {
        showToastMessage(message);
    }

    @Override
    public void showMessage(int resID) {
        showToastMessage(resID);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setError(EditText editText, int resId) {

    }

    @Override
    public void addFragment(List<Fragment> fragmentList, List<String> fragmentTitles) {
        pagingAdapter.addFragment(fragmentList, fragmentTitles);
        viewPager.setAdapter(pagingAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void updateAddToFavouriteBtn() {

    }

}
