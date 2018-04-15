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
import com.spade.nrc.ui.explore.view.ShowsAdapter;
import com.spade.nrc.ui.profile.presenter.UserFavouriteShowsPresenter;
import com.spade.nrc.ui.profile.presenter.UserFavouriteShowsPresenterImpl;
import com.spade.nrc.ui.profile.view.UserFavouriteShowsView;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 4/2/18.
 */

public class FavouriteShowFragment extends BaseFragment implements UserFavouriteShowsView {

    private View channelsView;
    private UserFavouriteShowsPresenter userFavouriteShowsPresenter;
    private ShowsAdapter showsAdapter;
    private List<Show> shows = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void initPresenter() {
        userFavouriteShowsPresenter = new UserFavouriteShowsPresenterImpl(getContext());
        userFavouriteShowsPresenter.setView(this);
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
        showsAdapter = new ShowsAdapter(getContext(), shows, Constants.SEARCH_SHOW_TYPE);
        channelsRecyclerView.setAdapter(showsAdapter);
        userFavouriteShowsPresenter.getUSerShows();
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
    public void showShows(List<Show> showList) {
        this.shows.clear();
        this.shows.addAll(showList);
        showsAdapter.notifyDataSetChanged();
    }
}
