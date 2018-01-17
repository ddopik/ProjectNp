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
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.general.PagingAdapter;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.GlideApp;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/14/18.
 */

public class ChannelDetailsFragment extends BaseFragment implements ChannelsDetailsView {

    private ChannelDetailsPresenter channelDetailsPresenter;
    private View channelDetailsView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView channelTitle;
    private ImageView channelLogo, channelBackground, channelIllustration;
    private PagingAdapter pagingAdapter;
    private int channelID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        channelDetailsView = inflater.inflate(R.layout.fragment_channel, container, false);
        initViews();
        return channelDetailsView;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        channelBackground = channelDetailsView.findViewById(R.id.channel_background);
        channelIllustration = channelDetailsView.findViewById(R.id.channel_illustration);
        channelLogo = channelDetailsView.findViewById(R.id.channel_logo);
        channelTitle = channelDetailsView.findViewById(R.id.channel_title);
        viewPager = channelDetailsView.findViewById(R.id.fragments_viewpager);
        tabLayout = channelDetailsView.findViewById(R.id.tabs);

        channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        setChannelTheme();

        pagingAdapter = new PagingAdapter(getChildFragmentManager());
        channelDetailsPresenter.setUpViewPager(channelID);

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resID) {

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

//    public void setChannelID(int channelID) {
//        this.channelID = channelID;
//    }

    @Override
    public void addFragment(List<Fragment> fragmentList, List<String> fragmentTitles) {
        pagingAdapter.addFragment(fragmentList, fragmentTitles);
        viewPager.setAdapter(pagingAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setChannelTheme() {
        switch (channelID) {
            case Constants.MEGA_FM_ID:
                channelTitle.setText(getString(R.string.mega_fm));
                GlideApp.with(getContext()).load(R.drawable.ic_mega_logo).into(channelLogo);
                GlideApp.with(getContext()).load(R.drawable.mega_gradient_background).into(channelBackground);
                GlideApp.with(getContext()).load(R.drawable.mega_bg).into(channelIllustration);
                tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.mega_color_list));
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.mega_second_color));
                break;
            case Constants.NAGHAM_ID:
                channelTitle.setText(getString(R.string.nagham));
                GlideApp.with(getContext()).load(R.drawable.ic_nagham_logo).into(channelLogo);
                GlideApp.with(getContext()).load(R.drawable.nagham_background).into(channelBackground);
                GlideApp.with(getContext()).load(R.drawable.nagham_bg).into(channelIllustration);
                tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.nagham_color_list));
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.nagham_second_color));
                break;
            case Constants.SH3BY_ID:
                channelTitle.setText(getString(R.string.sh3by));
                GlideApp.with(getContext()).load(R.drawable.ic_sh3by_logo).into(channelLogo);
                GlideApp.with(getContext()).load(R.drawable.sh3by_gradient_background).into(channelBackground);
                GlideApp.with(getContext()).load(R.drawable.sh3by_bg).into(channelIllustration);
                tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.sh3by_color_list));
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.sh3by_95_second_color));
                break;
            case Constants.RADIO_HITS_ID:
                channelTitle.setText(getString(R.string.radio_hits));
                GlideApp.with(getContext()).load(R.drawable.ic_radio_logo).into(channelLogo);
                GlideApp.with(getContext()).load(R.drawable.radio_hits_gradient_background).into(channelBackground);
                GlideApp.with(getContext()).load(R.drawable.radio_bg).into(channelIllustration);
                tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.radio_color_list));
                tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.radio_hits_second_color));
                channelIllustration.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                channelIllustration.requestLayout();
                break;

        }
    }
}
