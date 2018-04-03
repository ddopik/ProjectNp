package com.spade.nrc.ui.presenters.view;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.event.bus.events.PresenterClickEvent;
import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.ui.presenters.model.PresenterData;
import com.spade.nrc.ui.presenters.presenter.PresentersPresenter;
import com.spade.nrc.ui.presenters.presenter.PresentersPresenterImpl;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.PrefUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public class PresentersFragment extends BaseFragment implements PresentersView, PresentersAdapter.OnPresenterClicked {

    private View presenterView;
    private ProgressBar progressBar;
    private PresentersAdapter presentersAdapter;
    private List<Presenter> presenterList = new ArrayList<>();
    private PresentersPresenter presentersPresenter;
    private EventBus eventBus;
    private boolean isLoading = false;
    private int currentPage = 0, lastPage, channelID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenterView = inflater.inflate(R.layout.fragment_listing, container, false);
        initViews();
        return presenterView;
    }

    @Override
    protected void initPresenter() {
        presentersPresenter = new PresentersPresenterImpl(getContext());
        presentersPresenter.setView(this);
        eventBus = EventBus.getDefault();
    }

    @Override
    protected void initViews() {
        CustomRecyclerView presentersRecycle = presenterView.findViewById(R.id.items_recycler);
        presentersRecycle.setNestedScrollingEnabled(false);
        progressBar = presenterView.findViewById(R.id.progress_bar);

        channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources()
                .getColor(ChannelUtils.getChannelSecondaryColor(channelID)), PorterDuff.Mode.SRC_IN);
        presentersAdapter = new PresentersAdapter(getContext(), presenterList, channelID);
        presentersAdapter.setOnPresenterClicked(this);
        presentersRecycle.setAdapter(presentersAdapter);
        presentersRecycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = presentersRecycle.getLayoutManager().getChildCount();
                int totalItemCount = presentersRecycle.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) presentersRecycle.getLayoutManager()).findFirstVisibleItemPosition();

                if (!isLoading && (currentPage < lastPage)) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        getPresenters();
                    }
                }
            }
        });
        getPresenters();
        sendAnalytics(String.format(getString(R.string.channel_presenters_analytics), getString(ChannelUtils.getChannelTitle(channelID))));
    }

    private void getPresenters() {
        currentPage += 1;
        isLoading = true;
        presentersPresenter.getPresenters(PrefUtils.getAppLang(getContext()), channelID, currentPage);
    }

    private void sendAnalytics(String screenName) {
        Tracker causesTracker = NRCApplication.getDefaultTracker();
        causesTracker.setScreenName(screenName);
        causesTracker.send(new HitBuilders.ScreenViewBuilder().build());
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
    public void showPresenters(PresenterData presenterData) {
        lastPage = presenterData.getLastPage();
        isLoading = false;
        if (presenterData.getPresenters() != null)
            this.presenterList.addAll(presenterData.getPresenters());
        presentersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPresenterClicked(int presenterID) {
        eventBus.post(new PresenterClickEvent(presenterID, channelID, true));
    }
}
