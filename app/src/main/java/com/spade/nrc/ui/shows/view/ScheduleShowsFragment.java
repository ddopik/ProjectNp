package com.spade.nrc.ui.shows.view;

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
import com.spade.nrc.ui.ads.AdModel;
import com.spade.nrc.ui.event.bus.events.ShowsClickEvent;
import com.spade.nrc.ui.explore.view.ShowsAdapter;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.ui.shows.model.ShowsData;
import com.spade.nrc.ui.shows.presenter.ShowsPresenter;
import com.spade.nrc.ui.shows.presenter.ShowsPresenterImpl;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.PrefUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public class ScheduleShowsFragment extends BaseFragment implements ShowsView, ShowsAdapter.ShowActions {
    private ShowsPresenter showsPresenter;
    private View showsView;
    private ShowsAdapter showsAdapter;
    private List<Show> showList = new ArrayList<>();
    private List<AdModel> adModelList = new ArrayList<>();
    private ProgressBar progressBar;
    private EventBus eventBus;
    private boolean isLoading = false;
    private int currentPage = 0, lastPage, type, channelID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showsView = inflater.inflate(R.layout.fragment_listing, container, false);
        initViews();
        return showsView;
    }

    @Override
    protected void initPresenter() {
        showsPresenter = new ShowsPresenterImpl(getContext());
        showsPresenter.setView(this);
        eventBus = EventBus.getDefault();
    }


    @Override
    protected void initViews() {
        CustomRecyclerView showsRecycler = showsView.findViewById(R.id.items_recycler);
        progressBar = showsView.findViewById(R.id.progress_bar);

        showsRecycler.setNestedScrollingEnabled(false);

        channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        type = getArguments().getInt(Constants.EXTRA_SHOW_TYPE);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources()
                .getColor(ChannelUtils.getChannelSecondaryColor(channelID)), PorterDuff.Mode.SRC_IN);
        showsAdapter = new ShowsAdapter(getContext(), showList, type);
        showsAdapter.setShowActions(this);

        showsRecycler.setAdapter(showsAdapter);


        showsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = showsRecycler.getLayoutManager().getChildCount();
                int totalItemCount = showsRecycler.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) showsRecycler.getLayoutManager()).findFirstVisibleItemPosition();

                if (!isLoading && (currentPage < lastPage)) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        getShows();
                    }
                }
            }
        });

        getShows();
        sendAnalytics(String.format(getString(R.string.channel_shows_analytics), getString(ChannelUtils.getChannelTitle(channelID))));
    }


    private void getShows() {
        currentPage += 1;
        isLoading = true;

//        switch (type) {
//            case Constants.NORMAL_SHOW_TYPE:
//                showsPresenter.getShows(PrefUtils.getAppLang(getContext()), channelID, currentPage);
//                break;
//            case Constants.SCHEDULE_SHOW_TYPE:
        String day = getArguments().getString(Constants.EXTRA_DAY);
        showsPresenter.getShowsByDay(PrefUtils.getAppLang(getContext()), day, channelID, currentPage);
//                break;
//        }
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
    public void displayShows(List<Show> showList) {
        this.showList.clear();
        if (showList != null)
            this.showList.addAll(showList);
        showsAdapter.notifyDataSetChanged();
    }

    @Override
    public void displayShows(ShowsData showsData) {
        lastPage = showsData.getLastPage();
        isLoading = false;
        if (showList != null)
            this.showList.addAll(showsData.getShows());
    }

    @Override
    public void displayAds(List<AdModel> adModels) {
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateAddToFavouriteBtn() {
        showsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowClicked(Show show) {
        eventBus.post(new ShowsClickEvent(show.getId(), show.getChannel().getId(), true));
    }

    @Override
    public void onFavClicked(int showID) {
        showsPresenter.addShowToFav(showID);
    }

}
