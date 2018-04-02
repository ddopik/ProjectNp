package com.spade.nrc.ui.about.nrc;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.general.NavigationManager;

/**
 * Created by Ayman Abouzeid on 2/26/18.
 */

public class AboutNrcFragment extends BaseFragment {

    private View aboutNrcView;
    private NavigationManager.OnMenuOpenClicked onMenuOpenClicked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        aboutNrcView = inflater.inflate(R.layout.fragment_about_nrc, container, false);
        initViews();
        return aboutNrcView;
    }

    @Override
    protected void initPresenter() {
        sendAnalytics();
    }

    @SuppressLint("Recycle")
    @Override
    protected void initViews() {
        RecyclerView channelsRecyclerView = aboutNrcView.findViewById(R.id.channels_recycler_view);
        ImageView menuImageView = aboutNrcView.findViewById(R.id.menu_image_view);

        TypedArray typedArray = getResources().obtainTypedArray(R.array.channels_icons);
        ChannelsAdapter channelsAdapter = new ChannelsAdapter(getContext(), typedArray);
        channelsRecyclerView.setAdapter(channelsAdapter);

        menuImageView.setOnClickListener(view -> onMenuOpenClicked.onMenuImageClicked());
    }

    private void sendAnalytics() {
        Tracker causesTracker = NRCApplication.getDefaultTracker();
        causesTracker.setScreenName(getContext().getString(R.string.about_nrc_analytics));
        causesTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void setOnMenuOpenClicked(NavigationManager.OnMenuOpenClicked onMenuOpenClicked) {
        this.onMenuOpenClicked = onMenuOpenClicked;
    }
}
