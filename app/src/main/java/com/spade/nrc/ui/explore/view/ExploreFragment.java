package com.spade.nrc.ui.explore.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.media.player.MediaPlayerTrack;
import com.spade.nrc.nrc.media.player.MediaPlayerEvent;
import com.spade.nrc.realm.RealmDbImpl;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.about.nrc.AboutNrcFragment;
import com.spade.nrc.ui.event.bus.events.ShowsClickEvent;
import com.spade.nrc.ui.explore.model.LiveShowsData;
import com.spade.nrc.ui.explore.model.SlideBanner;
import com.spade.nrc.ui.explore.presenter.ExplorePresenter;
import com.spade.nrc.ui.explore.presenter.ExplorePresenterImpl;
import com.spade.nrc.ui.general.NavigationManager;
import com.spade.nrc.ui.main.ChannelNavigationInterface;
import com.spade.nrc.ui.main.MainActivity;
import com.spade.nrc.ui.profile.ProfileFragment;
import com.spade.nrc.ui.shows.model.Schedule;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.PrefUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.spade.nrc.utils.Constants.FEATURED_SHOW_TYPE;

/**
 * Created by Ayman Abouzeid on 1/6/18.
 */

public class ExploreFragment extends BaseFragment implements ExploreView, View.OnClickListener,
        ShowsAdapter.ShowActions, LiveShowsAdapter.LiveShowActions {

    private View exploreView;
    private ExplorePresenter explorePresenter;
    private ProgressBar sliderProgress, liveProgress, featuredProgress;
    private ImageView searchIcon, profileImage;
    private ShowsAdapter featuredShowsAdapter;
    private LiveShowsAdapter liveShowsAdapter;


    private SlidingBannerAdapter slidingBannerAdapter;
    private List<LiveShowsData> liveNowShows;
    private List<Show> featuredShows;
    private List<SlideBanner> slideBannerList;
    private ChannelNavigationInterface channelNavigationInterface;
    private RelativeLayout featuredShowsLayout, liveShowsLayout;
    private NavigationManager navigationManager;
    private NavigationManager.OnMenuOpenClicked onMenuOpenClicked;
    private EventBus eventBus;
    private RealmDbImpl realmDb;


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

        if (showList.isEmpty())
            featuredShowsLayout.setVisibility(View.GONE);

        featuredShowsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLiveNowShows(List<LiveShowsData> showList) {
        this.liveNowShows.clear();
        this.liveNowShows.addAll(showList);

        if (showList.isEmpty())
            liveShowsLayout.setVisibility(View.GONE);

        liveShowsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProfilePic(String url, RequestOptions requestOptions) {
        profileImage.setVisibility(View.VISIBLE);
        GlideApp.with(getActivity()).load(url).apply(requestOptions).centerCrop()
                .placeholder(getActivity().getResources().getDrawable(R.drawable.ic_profile_default)).apply(requestOptions)
                .into(profileImage);
    }

    @Override
    public void hideProfilePic() {
        profileImage.setVisibility(View.GONE);
    }

    @Override
    protected void initPresenter() {
        explorePresenter = new ExplorePresenterImpl(getContext());
        explorePresenter.setView(this);
        eventBus = EventBus.getDefault();
        sendAnalytics(getString(R.string.explore));
    }

    private void sendAnalytics(String screenName) {
        Tracker causesTracker = NRCApplication.getDefaultTracker();
        causesTracker.setScreenName(screenName);
        causesTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void initViews() {
//        Toolbar toolbar = exploreView.findViewById(R.id.toolbar);
        navigationManager = new NavigationManager(((MainActivity) getActivity()));
        CustomRecyclerView featuredRecycler = exploreView.findViewById(R.id.featured_shows_recycler);
        CustomRecyclerView liveShowsRecycler = exploreView.findViewById(R.id.live_now_recycler_view);
        ImageView radioChannelImageView = exploreView.findViewById(R.id.radio_channel_image_view);
        ImageView sh3byChannelImageView = exploreView.findViewById(R.id.sh3by_channel_image_view);
        ImageView naghamChannelImageView = exploreView.findViewById(R.id.nagham_channel_image_view);
        ImageView megaChannelImageView = exploreView.findViewById(R.id.mega_channel_image_view);
        ImageView menuImageView = exploreView.findViewById(R.id.menu_image_view);
        searchIcon = exploreView.findViewById(R.id.search_icon);
        profileImage = exploreView.findViewById(R.id.profile_image);
        ViewPager sliderPager = exploreView.findViewById(R.id.slider_pager);


        sliderProgress = exploreView.findViewById(R.id.slider_progress);
        liveProgress = exploreView.findViewById(R.id.live_now_progress);
        featuredProgress = exploreView.findViewById(R.id.featured_show_progress);
        featuredShowsLayout = exploreView.findViewById(R.id.featured_show_layout);
        liveShowsLayout = exploreView.findViewById(R.id.live_show_layout);

        menuImageView.setOnClickListener(this);
        radioChannelImageView.setOnClickListener(this);
        sh3byChannelImageView.setOnClickListener(this);
        naghamChannelImageView.setOnClickListener(this);
        megaChannelImageView.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        profileImage.setOnClickListener(this);

        featuredShows = new ArrayList<>();
        liveNowShows = new ArrayList<>();
        slideBannerList = new ArrayList<>();

        slidingBannerAdapter = new SlidingBannerAdapter(getContext(), slideBannerList);
        sliderPager.setAdapter(slidingBannerAdapter);

        liveShowsAdapter = new LiveShowsAdapter(getContext(), liveNowShows);
        liveShowsAdapter.setShowActions(this);
        liveShowsRecycler.setAdapter(liveShowsAdapter);

        featuredShowsAdapter = new ShowsAdapter(getContext(), featuredShows, FEATURED_SHOW_TYPE);
        featuredShowsAdapter.setShowActions(this);
        featuredRecycler.setAdapter(featuredShowsAdapter);


        explorePresenter.getProfilePic();
        explorePresenter.getSlidingBanners();
        explorePresenter.getLiveNowShows();
        explorePresenter.getFeaturedShows();

    }


    public void setChannelNavigationInterface(ChannelNavigationInterface channelNavigationInterface) {
        this.channelNavigationInterface = channelNavigationInterface;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_channel_image_view:
                channelNavigationInterface.openChannel(Constants.RADIO_HITS_ID);
                break;
            case R.id.sh3by_channel_image_view:
                channelNavigationInterface.openChannel(Constants.SH3BY_ID);
                break;
            case R.id.nagham_channel_image_view:
                channelNavigationInterface.openChannel(Constants.NAGHAM_ID);
                break;
            case R.id.mega_channel_image_view:
                channelNavigationInterface.openChannel(Constants.MEGA_FM_ID);
                break;
            case R.id.menu_image_view:
                onMenuOpenClicked.onMenuImageClicked();
                break;
            case R.id.search_icon:
                channelNavigationInterface.openSearchFragment();
//                navigationManager.openFragment(new SearchFragment(),R.id.fragment_container,ExploreFragment.class.getSimpleName());
                break;
            case R.id.profile_image:
                ProfileFragment profileFragment = new ProfileFragment();
                navigationManager.openFragment(profileFragment, R.id.fragment_container, AboutNrcFragment.class.getSimpleName());
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!eventBus.isRegistered(this))
            eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        if (eventBus.isRegistered(this))
            eventBus.unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MediaPlayerEvent mediaPlayerEvent) {
        if (liveShowsAdapter != null)
            liveShowsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onShowClicked(Show show) {
        eventBus.post(new ShowsClickEvent(show.getId(), show.getChannel().getId(), true));
    }

    @Override
    public void onFavClicked(int showID) {

    }

    @Override
    public void onLiveShowClicked(Show show, int channelID) {
        eventBus.post(new ShowsClickEvent(show.getId(), channelID, true));
    }

    @Override
    public void onPlayClicked(Show show, int channelID) {
        String mediaTitle;
        List<Schedule> scheduleList = new ArrayList<>();
        if (show != null) {
            mediaTitle = show.getTitle();
            scheduleList.addAll(show.getSchedules());
        } else {
            mediaTitle = String.format(getString(R.string.enjoy_listening)
                    , getString(ChannelUtils.getChannelTitle(channelID)));
        }
        MediaPlayerTrack mediaPlayerTrack = new MediaPlayerTrack(channelID, mediaTitle, scheduleList);
        eventBus.post(mediaPlayerTrack);
    }

    public void setOnMenuOpenClicked(NavigationManager.OnMenuOpenClicked onMenuOpenClicked) {
        this.onMenuOpenClicked = onMenuOpenClicked;
    }
}
