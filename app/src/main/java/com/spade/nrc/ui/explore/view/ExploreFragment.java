package com.spade.nrc.ui.explore.view;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.explore.model.SlideBanner;
import com.spade.nrc.ui.explore.presenter.ExplorePresenter;
import com.spade.nrc.ui.explore.presenter.ExplorePresenterImpl;
import com.spade.nrc.ui.shows.model.Show;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.spade.nrc.utils.Constants.*;

/**
 * Created by Ayman Abouzeid on 1/6/18.
 */

public class ExploreFragment extends BaseFragment implements ExploreView {

    private View exploreView;
    private ExplorePresenter explorePresenter;
    private ProgressBar sliderProgress, liveProgress, featuredProgress;
    private ShowsAdapter liveShowsAdapter, featuredShowsAdapter;
    private SlidingBannerAdapter slidingBannerAdapter;
    private List<Show> liveNowShows, featuredShows;
    private List<SlideBanner> slideBannerList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        exploreView = inflater.inflate(R.layout.fragment_explore, container, false);
        initViews();
        return exploreView;
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

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setError(EditText editText, int resId) {

    }

    @Override
    public void showSliderProgress() {
        sliderProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSliderProgress() {
        sliderProgress.setVisibility(View.GONE);
    }


    @Override
    public void showFeaturedShowsProgress() {
        featuredProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFeaturedShowsProgress() {
        featuredProgress.setVisibility(View.GONE);
    }

    @Override
    public void showLiveShowsProgress() {
        liveProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLiveShowsProgress() {
        liveProgress.setVisibility(View.GONE);
    }

    @Override
    public void showSlides(List<SlideBanner> slideBanners) {
        this.slideBannerList.clear();
        this.slideBannerList.addAll(slideBanners);
        slidingBannerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFeaturedShows(List<Show> showList) {
        this.featuredShows.clear();
        this.featuredShows.addAll(showList);
        featuredShowsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLiveNowShows(List<Show> showList) {
        this.liveNowShows.clear();
        this.liveNowShows.addAll(showList);
        liveShowsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initPresenter() {
        explorePresenter = new ExplorePresenterImpl(getContext());
        explorePresenter.setView(this);
    }

    @Override
    protected void initViews() {
        CustomRecyclerView featuredRecycler = exploreView.findViewById(R.id.featured_shows_recycler);
        CustomRecyclerView liveShowsRecycler = exploreView.findViewById(R.id.live_now_recycler_view);
        ViewPager sliderPager = exploreView.findViewById(R.id.slider_pager);

        sliderProgress = exploreView.findViewById(R.id.slider_progress);
        liveProgress = exploreView.findViewById(R.id.live_now_progress);
        featuredProgress = exploreView.findViewById(R.id.featured_show_progress);

        featuredShows = new ArrayList<>();
        liveNowShows = new ArrayList<>();
        slideBannerList = new ArrayList<>();

        slidingBannerAdapter = new SlidingBannerAdapter(getContext(), slideBannerList);
        sliderPager.setAdapter(slidingBannerAdapter);

        int defaultColor = ContextCompat.getColor(getContext(), R.color.black);
        liveShowsAdapter = new ShowsAdapter(getContext(), liveNowShows, LIVE_SHOW_TYPE, defaultColor);
        liveShowsRecycler.setAdapter(liveShowsAdapter);

        featuredShowsAdapter = new ShowsAdapter(getContext(), featuredShows, FEATURED_SHOW_TYPE, defaultColor);
        featuredRecycler.setAdapter(featuredShowsAdapter);

        explorePresenter.getSlidingBanners();
        explorePresenter.getLiveNowShows();
        explorePresenter.getFeaturedShows();

//        MediaPlayer mediaPlayer = new MediaPlayer();
//        try {
//            mediaPlayer.setDataSource("https://ahmsamir.radioca.st/stream");
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.start();
    }
}
