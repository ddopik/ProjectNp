package com.spade.nrc.ui.shows.view;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.event.bus.events.ShowsClickEvent;
import com.spade.nrc.ui.explore.view.ShowsAdapter;
import com.spade.nrc.ui.shows.model.Show;
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

public class ShowsFragment extends BaseFragment implements ShowsView, ShowsAdapter.ShowActions {
    private ShowsPresenter showsPresenter;
    private View showsView;
    private ShowsAdapter showsAdapter;
    private List<Show> showList = new ArrayList<>();
    private ProgressBar progressBar;
    private EventBus eventBus;

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
        eventBus = EventBus.getDefault();
    }

    @Override
    protected void initViews() {
        CustomRecyclerView showsRecycler = showsView.findViewById(R.id.items_recycler);
        progressBar = showsView.findViewById(R.id.progress_bar);

        showsRecycler.setNestedScrollingEnabled(false);

        int channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        int type = getArguments().getInt(Constants.EXTRA_SHOW_TYPE);
        int channelColor = ContextCompat.getColor(getContext(), ChannelUtils.getChannelSecondaryColor(channelID));
        progressBar.getIndeterminateDrawable().setColorFilter(getResources()
                .getColor(ChannelUtils.getChannelSecondaryColor(channelID)), PorterDuff.Mode.SRC_IN);
        //
//        switch (channelID) {
//            case Constants.MEGA_FM_ID:
//                channelColor = ContextCompat.getColor(getContext(), R.color.mega_second_color);
//                break;
//            case Constants.NAGHAM_ID:
//                channelColor = ContextCompat.getColor(getContext(), R.color.nagham_second_color);
//                break;
//            case Constants.RADIO_HITS_ID:
//                channelColor = ContextCompat.getColor(getContext(), R.color.radio_hits_second_color);
//                break;
//            case Constants.SH3BY_ID:
//                channelColor = ContextCompat.getColor(getContext(), R.color.sh3by_95_second_color);
//                break;
//        }

        showsAdapter = new ShowsAdapter(getContext(), showList, type);
        showsAdapter.setShowActions(this);

        showsRecycler.setAdapter(showsAdapter);

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
        if (showList != null)
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

    @Override
    public void onShowClicked(Show show) {
        eventBus.post(new ShowsClickEvent(show.getId(), show.getChannel().getId(), true));
    }

//    @Override
//    public void onPlayClicked(Show show) {
//
//    }
}
