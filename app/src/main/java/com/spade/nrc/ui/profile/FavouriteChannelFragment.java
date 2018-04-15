package com.spade.nrc.ui.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.profile.presenter.UserFavouriteChannelsPresenter;
import com.spade.nrc.ui.profile.presenter.UserFavouriteChannelsPresenterImpl;
import com.spade.nrc.ui.profile.view.UserFavouriteChannelsView;
import com.spade.nrc.ui.search.view.channelsSearch.ChannelsSearchAdapter;
import com.spade.nrc.ui.shows.model.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 4/2/18.
 */

public class FavouriteChannelFragment extends BaseFragment implements UserFavouriteChannelsView {

    private View channelsView;
    private UserFavouriteChannelsPresenter userFavouriteChannelsPresenter;
    private ChannelsSearchAdapter channelsSearchAdapter;
    private List<Channel> channelList = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void initPresenter() {
        userFavouriteChannelsPresenter = new UserFavouriteChannelsPresenterImpl(getContext());
        userFavouriteChannelsPresenter.setView(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        channelsView = inflater.inflate(R.layout.list_layout, container, false);
        initViews();
        return channelsView;
    }

    @Override
    protected void initViews() {
        progressBar = channelsView.findViewById(R.id.progress_bar);
        CustomRecyclerView channelsRecyclerView = channelsView.findViewById(R.id.items_recycler);
        channelsSearchAdapter = new ChannelsSearchAdapter(getContext(), channelList);
        channelsRecyclerView.setAdapter(channelsSearchAdapter);
        userFavouriteChannelsPresenter.getUSerChannels();
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
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setError(EditText editText, int resId) {

    }

    @Override
    public void showChannels(List<Channel> channelList) {
        this.channelList.clear();
        this.channelList.addAll(channelList);
        channelsSearchAdapter.notifyDataSetChanged();
    }
}
