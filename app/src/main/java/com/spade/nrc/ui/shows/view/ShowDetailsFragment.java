package com.spade.nrc.ui.shows.view;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.ui.presenters.view.PresentersAdapter;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.ui.shows.presenter.ShowDetailsPresenterImpl;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/26/18.
 */

public class ShowDetailsFragment extends BaseFragment implements ShowDetailsView {

    private View showDetailsView;
    private ShowDetailsPresenterImpl showDetailsPresenter;
    private PresentersAdapter presentersAdapter;
    private LinearLayout presentersLayout;
    private ImageView showImage;
    private TextView showName, aboutShow, showDays, showTimes;
    private ProgressBar progressBar;
    private List<Presenter> presenterList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showDetailsView = inflater.inflate(R.layout.fragment_show_details, container, false);
        initViews();
        return showDetailsView;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resID) {

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
    protected void initPresenter() {
        showDetailsPresenter = new ShowDetailsPresenterImpl(getContext());
        showDetailsPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        CollapsingToolbarLayout collapsingToolbarLayout = showDetailsView.findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = showDetailsView.findViewById(R.id.appBar);
//        collapsingToolbarLayout.setTitle("Your Title");
//        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent)); // transperent color = #00000000
//        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0)); //Color of your title
        CustomRecyclerView showPresentersRecyclerView = showDetailsView.findViewById(R.id.show_presenters_recycler_view);
        Toolbar toolbar = showDetailsView.findViewById(R.id.toolbar);
        ImageView backBtn = toolbar.findViewById(R.id.back_button);

        progressBar = showDetailsView.findViewById(R.id.progress_bar);
        showName = showDetailsView.findViewById(R.id.show_title);
        aboutShow = showDetailsView.findViewById(R.id.about_show_text_view);
        showImage = showDetailsView.findViewById(R.id.show_image);
        showDays = showDetailsView.findViewById(R.id.show_days);
        showTimes = showDetailsView.findViewById(R.id.show_times);
        presentersLayout = showDetailsView.findViewById(R.id.presenters_layout);
        int channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        int showID = getArguments().getInt(Constants.EXTRA_SHOW_ID);
        showName.setTextColor(ContextCompat.getColor(getContext(), ChannelUtils.getChannelPrimaryColor(channelID)));
        showTimes.setTextColor(ContextCompat.getColor(getContext(), ChannelUtils.getChannelSecondaryColor(channelID)));

//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//            }
//        });
        progressBar.getIndeterminateDrawable().setColorFilter(getResources()
                .getColor(ChannelUtils.getChannelSecondaryColor(channelID)), PorterDuff.Mode.SRC_IN);
        presentersAdapter = new PresentersAdapter(getContext(), presenterList, channelID);
        showPresentersRecyclerView.setAdapter(presentersAdapter);
        showDetailsPresenter.getShowDetails(String.valueOf(showID));
        backBtn.setOnClickListener(view -> getActivity().onBackPressed());
    }


    @Override
    public void displayShowData(Show show) {
        showName.setText(show.getTitle());
        aboutShow.setText(show.getDescription());
        if (show.getSchedules() != null && !show.getSchedules().isEmpty()) {
            showTimes.setText(TextUtils.getScheduleTimes(show.getSchedules()));
            showDays.setText(show.getSchedules().get(0).getDay());
        } else {
            showTimes.setVisibility(View.GONE);
            showDays.setVisibility(View.GONE);
        }

        GlideApp.with(getContext())
                .load(show.getMedia()).placeholder(ChannelUtils.getShowDefaultImage(show.getChannel().getId())).into(showImage);
        showImage.setBackgroundColor(ContextCompat.getColor(getContext(), ChannelUtils.getChannelSecondaryColor(show.getChannel().getId())));
        presenterList.addAll(show.getPresenters());
        presentersAdapter.notifyDataSetChanged();

        if (presenterList.isEmpty())
            presentersLayout.setVisibility(View.GONE);
    }
}
