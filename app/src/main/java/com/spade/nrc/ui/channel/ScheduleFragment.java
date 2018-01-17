package com.spade.nrc.ui.channel;

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

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.general.PagingAdapter;
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
    private int channelID;

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
        viewPager = scheduleView.findViewById(R.id.fragments_viewpager);
        tabLayout = scheduleView.findViewById(R.id.tabs);
        pagingAdapter = new PagingAdapter(getChildFragmentManager());
        channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        setChannelTheme();
        schedulePresenter.setUpViewPager(channelID);
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

    private void setChannelTheme() {
        switch (channelID) {
            case Constants.MEGA_FM_ID:
                tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.mega_schedule_color_list));
                break;
            case Constants.NAGHAM_ID:
                tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.nagham_schedule_color_list));
                break;
            case Constants.SH3BY_ID:
                tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.sh3by_schedule_color_list));
                break;
            case Constants.RADIO_HITS_ID:
                tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.radio_schedule_color_list));
                break;

        }
    }
}
