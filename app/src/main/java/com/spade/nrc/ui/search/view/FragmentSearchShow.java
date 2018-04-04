package com.spade.nrc.ui.search.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.explore.view.ShowsAdapter;
import com.spade.nrc.ui.search.presenter.ShowSearchPresenter;
import com.spade.nrc.ui.search.presenter.ShowsSearchPresenterImpl;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class FragmentSearchShow extends BaseFragment implements FragmentSearchShowView {

    private View mainView;
    private ProgressBar progressBar;
    private ShowSearchPresenter showFragmentPresenter;
    private CustomRecyclerView customRecyclerView;
    private ShowsAdapter showsAdapter;
    private List<Show> showList = new ArrayList<Show>();
    int type;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_result_fragment, container, false);
        progressBar = mainView.findViewById(R.id.progress_bar);
        return mainView;
    }

    @Override
    protected void initPresenter() {
        showFragmentPresenter = new ShowsSearchPresenterImpl(this);
    }

    @Override
    protected void initViews() {
        customRecyclerView = mainView.findViewById(R.id.search_recycler_view);
        type = getArguments().getInt(Constants.EXTRA_SHOW_TYPE);
        showsAdapter = new ShowsAdapter(getActivity(), showList, type);
    }


    @Override
    public void ViewSearchShow(String key) {
         showFragmentPresenter.findShows(key);
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
    public void viewShowsList(List<Show> showList) {

        customRecyclerView.setVisibility(View.VISIBLE);
        this.showList = showList;
        showsAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewStateMessage(String msg) {
        super.showToastMessage(msg);
    }

    @Override
    public void hideShowsList() {
        customRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showToastMessage(String message) {
        super.showToastMessage(message);
    }

    @Override
    public void showToastMessage(int messageResID) {
        super.showToastMessage(messageResID);
    }


}
