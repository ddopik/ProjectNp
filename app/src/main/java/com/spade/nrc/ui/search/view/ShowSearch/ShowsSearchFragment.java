package com.spade.nrc.ui.search.view.ShowSearch;

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
import com.spade.nrc.ui.event.bus.events.ShowsClickEvent;
import com.spade.nrc.ui.explore.view.ShowsAdapter;
import com.spade.nrc.ui.search.presenter.showsPresenter.ShowSearchPresenter;
import com.spade.nrc.ui.search.presenter.showsPresenter.ShowsSearchPresenterImpl;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class ShowsSearchFragment extends BaseSearchFragment implements ShowsSearchView, ShowsAdapter.ShowActions {

    private View mainView;
    private ProgressBar progressBar;
    private ShowSearchPresenter showFragmentPresenter;
    private CustomRecyclerView customRecyclerView;
    private ShowsAdapter showsAdapter;
    private List<Show> showList = new ArrayList<>();
    private EventBus eventBus = EventBus.getDefault();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_show_fragment, container, false);
        progressBar = mainView.findViewById(R.id.progress_bar);
        initViews();
        return mainView;
    }

    @Override
    protected void initPresenter() {
        showFragmentPresenter = new ShowsSearchPresenterImpl();
        showFragmentPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        customRecyclerView = mainView.findViewById(R.id.search_recycler_view);
        showsAdapter = new ShowsAdapter(getContext(), showList, Constants.SEARCH_SHOW_TYPE);
        showsAdapter.setShowActions(this);
        customRecyclerView.setAdapter(showsAdapter);
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
        if (showFragmentPresenter != null)
            showFragmentPresenter.findShows(query);
    }

    @Override
    public void viewShowsList(List<Show> showList) {
        this.showList.clear();
        this.showList.addAll(showList);
        if (showList.isEmpty())
            hideLoading();
        else
            customRecyclerView.setVisibility(View.VISIBLE);
        showsAdapter.notifyDataSetChanged();

    }

    @Override
    public void hideShowsList() {
        customRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onShowClicked(Show show) {
        eventBus.post(new ShowsClickEvent(show.getId(), show.getChannel().getId(), true));

    }

    @Override
    public void onFavClicked(int showID) {

    }
}
