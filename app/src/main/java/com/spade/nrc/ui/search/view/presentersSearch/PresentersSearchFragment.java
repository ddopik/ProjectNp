package com.spade.nrc.ui.search.view.presentersSearch;

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
import com.spade.nrc.base.BaseSearchFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.event.bus.events.PresenterClickEvent;
import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.ui.presenters.view.PresentersAdapter;
import com.spade.nrc.ui.search.presenter.presentersPresenter.PresentersSearchPresenter;
import com.spade.nrc.ui.search.presenter.presentersPresenter.PresentersSearchPresenterImpl;
import com.spade.nrc.utils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class PresentersSearchFragment extends BaseSearchFragment implements PresentersSearchView, PresentersAdapter.OnPresenterClicked {


    private View mainView;
    private ProgressBar progressBar;
    private CustomRecyclerView customRecyclerView;
    private PresentersAdapter presentersAdapter;
    private PresentersSearchPresenter presentersSearchPresenter;
    private List<Presenter> presentersList = new ArrayList<>();
    private EventBus eventBus = EventBus.getDefault();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_result_fragment, container, false);
        initViews();
        return mainView;
    }

    @Override
    protected void initPresenter() {
        presentersSearchPresenter = new PresentersSearchPresenterImpl();
        presentersSearchPresenter.setView(this);
    }


    @Override
    protected void initViews() {
        customRecyclerView = mainView.findViewById(R.id.search_recycler_view);
        progressBar = mainView.findViewById(R.id.progress_bar);
        customRecyclerView = mainView.findViewById(R.id.search_recycler_view);
        presentersAdapter = new PresentersAdapter(getContext(), presentersList, Constants.NRC_ID);
        presentersAdapter.setOnPresenterClicked(this);
        customRecyclerView.setAdapter(presentersAdapter);
    }

    @Override
    public void viewPresentersList(List<Presenter> presentersList) {
        this.presentersList.clear();
        this.presentersList.addAll(presentersList);
        if (presentersList.isEmpty())
            hidePresentersList();
        else
            customRecyclerView.setVisibility(View.VISIBLE);
        presentersAdapter.notifyDataSetChanged();
    }




    @Override
    public void hidePresentersList() {
        customRecyclerView.setVisibility(View.GONE);
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
        if (presentersSearchPresenter != null)
            presentersSearchPresenter.findPresenter(query);
    }

    @Override
    public void onPresenterClicked(int presenterID) {
        eventBus.post(new PresenterClickEvent(presenterID, Constants.NRC_ID, true));
    }
}
