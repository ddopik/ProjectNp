package com.spade.nrc.ui.channel.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.channel.presenter.ChannelDetailsPresenter;
import com.spade.nrc.ui.channel.presenter.ChannelDetailsPresenterImpl;
import com.spade.nrc.ui.general.PagingAdapter;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;

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
    private ProgressDialog progressDialog;
    private TextView addToFavouriteChannel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        channelDetailsView = inflater.inflate(R.layout.fragment_channel, container, false);
        initViews();
        return channelDetailsView;
    }

    @Override
    protected void initPresenter() {
        channelDetailsPresenter = new ChannelDetailsPresenterImpl(getContext());
        channelDetailsPresenter.setView(this);
    }

    private void sendAnalytics(String channelName) {
        Tracker causesTracker = NRCApplication.getDefaultTracker();
        causesTracker.setScreenName(channelName);
        causesTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void initViews() {
        Toolbar toolbar = channelDetailsView.findViewById(R.id.toolbar);
        ImageView backBtn = toolbar.findViewById(R.id.back_button);
        channelBackground = channelDetailsView.findViewById(R.id.channel_background);
        channelIllustration = channelDetailsView.findViewById(R.id.channel_illustration);
        channelLogo = channelDetailsView.findViewById(R.id.channel_logo);
        channelTitle = channelDetailsView.findViewById(R.id.channel_title);
        viewPager = channelDetailsView.findViewById(R.id.fragments_viewpager);
        tabLayout = channelDetailsView.findViewById(R.id.tabs);
        addToFavouriteChannel = channelDetailsView.findViewById(R.id.add_to_favourite_btn);
        channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        sendAnalytics(getString(ChannelUtils.getChannelTitle(channelID)));
        setChannelTheme();

        pagingAdapter = new PagingAdapter(getChildFragmentManager());
        channelDetailsPresenter.setUpViewPager(channelID);

        backBtn.setOnClickListener(view -> getActivity().onBackPressed());
        addToFavouriteChannel.setOnClickListener(view -> channelDetailsPresenter.addChannelToFav(channelID));
        updateAddToFavouriteBtn();
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
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null)
            progressDialog.hide();
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
        boolean isLiked = channelDetailsPresenter.isLiked(channelID);
        int btnText = isLiked ? R.string.remove_from_fav : R.string.add_to_channels;
        addToFavouriteChannel.setText(btnText);
    }


    private void setChannelTheme() {
        channelTitle.setText(ChannelUtils.getChannelTitle(channelID));
        channelLogo.setImageResource(ChannelUtils.getChannelLogo(channelID));
        channelBackground.setImageResource(ChannelUtils.getChannelBackground(channelID));
        channelIllustration.setImageResource(ChannelUtils.getChannelIllustration(channelID));
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), ChannelUtils.getChannelDetailsColorList(channelID)));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), ChannelUtils.getChannelSecondaryColor(channelID)));

        switch (channelID) {
            case Constants.SH3BY_ID:
                channelIllustration.requestLayout();
                channelIllustration.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case Constants.RADIO_HITS_ID:
                channelIllustration.requestLayout();
                channelIllustration.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                channelIllustration.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
        }
    }
}
