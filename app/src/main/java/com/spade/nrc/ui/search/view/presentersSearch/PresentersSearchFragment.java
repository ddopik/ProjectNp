package com.spade.nrc.ui.search.view.presentersSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseSearchFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.ui.presenters.view.PresentersAdapter;
import com.spade.nrc.ui.search.presenter.presentersPresenter.PresentersSearchPresenter;
import com.spade.nrc.ui.search.presenter.presentersPresenter.PresentersSearchPresenterImpl;
import com.spade.nrc.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class PresentersSearchFragmentSearch extends BaseSearchFragment implements PresentersSearchView {


    private View mainView;
    private ProgressBar progressBar;
    private CustomRecyclerView customRecyclerView;
    private PresentersAdapter presentersAdapter;
    private PresentersSearchPresenter presentersSearchPresenter;
    private List<Presenter> presentersList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_result_fragment, container, false);
        initViews();
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
        customRecyclerView = mainView.findViewById(R.id.search_recycler_view);
        presentersAdapter = new PresentersAdapter(getContext(), presentersList, Constants.NRC_ID);
        customRecyclerView.setAdapter(presentersAdapter);
    }

    @Override
    public void viewPresentersList(List<Presenter> presentersList) {

        customRecyclerView.setVisibility(View.VISIBLE);
        this.presentersList.clear();
        this.presentersList.addAll(presentersList);
        presentersAdapter.notifyDataSetChanged();

    }


    @Override
    public void hidePresentersList() {
        customRecyclerView.setVisibility(View.GONE);
    }


    @Override
    protected void search(String query) {
        if (presentersSearchPresenter != null)
            presentersSearchPresenter.findPresenter(query);
    }
}
