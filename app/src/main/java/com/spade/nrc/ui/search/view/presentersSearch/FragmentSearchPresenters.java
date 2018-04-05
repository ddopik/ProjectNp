package com.spade.nrc.ui.search.view.presentersSearch;

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
import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.ui.presenters.view.PresentersAdapter;
import com.spade.nrc.ui.search.presenter.presentersPresenter.PresentersSearchPresenter;
import com.spade.nrc.ui.search.presenter.presentersPresenter.PresentersSearchPresenterImpl;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class FragmentSearchPresenters extends BaseFragment implements FragmentSearchPresentersView {


    private View mainView;
    private ProgressBar progressBar;
    private CustomRecyclerView customRecyclerView;
    private PresentersAdapter presentersAdapter;
    private PresentersSearchPresenter presentersSearchPresenter;
    private List<Presenter> presentersList = new ArrayList<Presenter>();
    public static final int FEATURED_SEARCH_TYPE = 5;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_result_fragment, container, false);
        customRecyclerView = mainView.findViewById(R.id.search_recycler_view);
        progressBar = mainView.findViewById(R.id.progress_bar);
        initViews();
        initPresenter();
        return mainView;
    }

    @Override
    protected void initPresenter() {

        presentersSearchPresenter = new PresentersSearchPresenterImpl(this);
    }


    @Override
    protected void initViews() {
        customRecyclerView = mainView.findViewById(R.id.search_recycler_view);
        progressBar = mainView.findViewById(R.id.progress_bar);
        presentersAdapter = new PresentersAdapter(getContext(), presentersList, FEATURED_SEARCH_TYPE);
        customRecyclerView.setAdapter(presentersAdapter);
    }


    @Override
    public void ViewSearchPresenters(String key) {
        if(presentersSearchPresenter !=null)
        presentersSearchPresenter.findPresenter(key);

    }

    @Override
    public void viewPresentersList(List<Presenter> presentersList) {

        customRecyclerView.setVisibility(View.VISIBLE);
        this.presentersList.clear();
        this.presentersList.addAll(presentersList);
        presentersAdapter.notifyDataSetChanged();

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
    public void hidePresentersList() {
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
}
