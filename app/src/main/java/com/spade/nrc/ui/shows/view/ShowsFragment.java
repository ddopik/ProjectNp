package com.spade.nrc.ui.shows.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.explore.view.ShowsAdapter;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.ui.shows.presenter.ShowsPresenter;
import com.spade.nrc.ui.shows.presenter.ShowsPresenterImpl;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public class ShowsFragment extends BaseFragment implements ShowsView {
    private ShowsPresenter showsPresenter;
    private View showsView;
    private ShowsAdapter showsAdapter;
    private List<Show> showList = new ArrayList<>();
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showsView = inflater.inflate(R.layout.fragment_listing, container, false);
        initViews();
        return showsView;
    }

    @Override
    protected void initPresenter() {
        showsPresenter = new ShowsPresenterImpl(getContext());
        showsPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        CustomRecyclerView presentersRecycle = showsView.findViewById(R.id.items_recycler);
        progressBar = showsView.findViewById(R.id.progress_bar);

        int channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        int type = getArguments().getInt(Constants.EXTRA_SHOW_TYPE);
        int channelColor = ContextCompat.getColor(getContext(), R.color.black);

        switch (channelID) {
            case Constants.MEGA_FM_ID:
                channelColor = ContextCompat.getColor(getContext(), R.color.mega_second_color);
                break;
            case Constants.NAGHAM_ID:
                channelColor = ContextCompat.getColor(getContext(), R.color.nagham_second_color);
                break;
            case Constants.RADIO_HITS_ID:
                channelColor = ContextCompat.getColor(getContext(), R.color.radio_hits_second_color);
                break;
            case Constants.SH3BY_ID:
                channelColor = ContextCompat.getColor(getContext(), R.color.sh3by_95_second_color);
                break;
        }

        showsAdapter = new ShowsAdapter(getContext(), showList, type, channelColor);
        presentersRecycle.setAdapter(showsAdapter);

        switch (type) {
            case Constants.NORMAL_SHOW_TYPE:
                showsPresenter.getShows(PrefUtils.getAppLang(getContext()), channelID);
                break;
            case Constants.SCHEDULE_SHOW_TYPE:
                String day = getArguments().getString(Constants.EXTRA_DAY);
                showsPresenter.getShowsByDay(PrefUtils.getAppLang(getContext()), day, channelID);
                break;
        }
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
    public void displayShows(List<Show> showList) {
        this.showList.clear();
        this.showList.addAll(showList);
        showsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }
}
