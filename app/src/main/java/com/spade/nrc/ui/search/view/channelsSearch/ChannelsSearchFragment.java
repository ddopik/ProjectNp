package com.spade.nrc.ui.search.view.channelsSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseSearchFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.search.presenter.channelsPresenter.ChannelsSearchPresenter;
import com.spade.nrc.ui.search.presenter.channelsPresenter.ChannelsSearchPresenterImpl;
import com.spade.nrc.ui.shows.model.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class ChannelsSearchFragment extends BaseSearchFragment implements ChannelsSearchView {


    private View mainView;
    private ProgressBar progressBar;
    private ChannelsSearchPresenter channelsSearchPresenter;
    private CustomRecyclerView channelsRecyclerView;
    private ChannelsSearchAdapter channelsAdapterAdapter;
    private List<Channel> channelList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_show_fragment, container, false);
        initViews();
        channelsSearchPresenter = new ChannelsSearchPresenterImpl();
        channelsSearchPresenter.setView(this);
        return mainView;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {
        channelsRecyclerView = mainView.findViewById(R.id.search_recycler_view);
        progressBar = mainView.findViewById(R.id.progress_bar);
        channelsAdapterAdapter = new ChannelsSearchAdapter(getContext(), channelList);
        channelsRecyclerView.setAdapter(channelsAdapterAdapter);
    }


    @Override
    public void viewChannelsList(List<Channel> channelList) {
        this.channelList.clear();
        this.channelList.addAll(channelList);

        if (channelList.isEmpty())
            hideChannelsList();
        else
            channelsRecyclerView.setVisibility(View.VISIBLE);
        channelsAdapterAdapter.notifyDataSetChanged();

    }

    @Override
    public void hideChannelsList() {
        channelsRecyclerView.setVisibility(View.GONE);
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
    public void search(String query) {
        if (channelsSearchPresenter != null)
            channelsSearchPresenter.findChannels(query);
    }
}
