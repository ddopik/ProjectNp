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
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.search.presenter.channelsPresenter.ChannelsSearchPresenter;
import com.spade.nrc.ui.search.presenter.channelsPresenter.ChannelsSearchPresenterImpl;
import com.spade.nrc.ui.search.view.ChannelsSearchAdapter;
import com.spade.nrc.ui.shows.model.Channel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class SearchChannelsFragment extends BaseFragment implements SearchChannelsView {


    private View mainView;
    private ProgressBar progressBar;
    private ChannelsSearchPresenter channelsSearchPresenter;
    private CustomRecyclerView customRecyclerView;
    private ChannelsSearchAdapter channelsAdapterAdapter;
    private ArrayList<Channel> channelList = new ArrayList<>();
    public static final int FEATURED_SHOW_TYPE = 0;
    private EventBus eventBus;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_show_fragment, container, false);
        initViews();
        return mainView;
    }

    @Override
    protected void initPresenter() {
        channelsSearchPresenter = new ChannelsSearchPresenterImpl();
        channelsSearchPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        customRecyclerView = mainView.findViewById(R.id.search_recycler_view);
        progressBar = mainView.findViewById(R.id.progress_bar);
        channelsAdapterAdapter = new ChannelsSearchAdapter(getContext(), channelList);
        customRecyclerView.setAdapter(channelsAdapterAdapter);

    }

    @Override
    public void viewSearchChannels(String key) { //-->Presenter Call
        channelsSearchPresenter.findChannels(key);
    }

    @Override
    public void viewChannelsList(List<Channel> channelList) {

        customRecyclerView.setVisibility(View.VISIBLE);
        this.channelList.clear();
        this.channelList.addAll(channelList);
        channelsAdapterAdapter.notifyDataSetChanged();

    }

    @Override
    public void hideChannelsList() {
        customRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void viewStateMessage(String msg) {
        super.showToastMessage(msg);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void showToastMessage(String message) {
        super.showToastMessage(message);
    }

    @Override
    public void showToastMessage(int messageResID) {
        super.showToastMessage(messageResID);
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
}
