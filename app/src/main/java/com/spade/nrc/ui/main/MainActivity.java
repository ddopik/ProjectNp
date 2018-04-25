package com.spade.nrc.ui.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.media.player.MediaPlayerTrack;
import com.spade.nrc.nrc.media.player.MediaInterface;
import com.spade.nrc.nrc.media.player.MediaPlayerEvent;
import com.spade.nrc.nrc.media.player.MusicProvider;
import com.spade.nrc.nrc.media.player.MusicService;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.about.nrc.AboutNrcFragment;
import com.spade.nrc.ui.channel.view.ChannelDetailsFragment;
import com.spade.nrc.ui.contact_us.view.ContactUsFragment;
import com.spade.nrc.ui.event.bus.events.PresenterClickEvent;
import com.spade.nrc.ui.event.bus.events.ShowsClickEvent;
import com.spade.nrc.ui.explore.view.ExploreFragment;
import com.spade.nrc.ui.explore.view.MenuAdapter;
import com.spade.nrc.ui.general.NavigationManager;
import com.spade.nrc.ui.general.NavigationManager.OnMenuOpenClicked;
import com.spade.nrc.ui.news.view.NewsFragment;
import com.spade.nrc.ui.player.PlayerFragment;
import com.spade.nrc.ui.player.ViewInnerChannelView;
import com.spade.nrc.ui.presenters.view.PresenterDetailsFragment;
import com.spade.nrc.ui.profile.ProfileFragment;
import com.spade.nrc.ui.search.view.SearchFragment;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.ui.shows.view.ShowDetailsFragment;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.LoginProviders;
import com.spade.nrc.utils.PrefUtils;
import com.spade.nrc.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ChannelNavigationInterface,
        MediaInterface, View.OnClickListener, MenuAdapter.OnItemClicked, OnMenuOpenClicked, AdapterView.OnItemSelectedListener {

    private String TAG = MainActivity.class.getSimpleName();

    private NavigationManager navigationManager;
    private MediaBrowserCompat mMediaBrowser;
    private MusicProvider musicProvider;
    private EventBus eventBus;

    private RelativeLayout footerPlayer;
    private LinearLayout menuOpenedLayout, userLogLayout;
    private FrameLayout playerFragment;
    private ImageView mediaControlButton, megaImage,
            radioHitsImageView, naghamImageView, sh3byImageView, loginImageView;
    private ImageView menuCollapsedImage;
    private ProgressBar playerProgressBar;
    private TextView showTitle, showTimes, exploreMenu, loginTextView;
    private DrawerLayout mDrawerLayout;
    private AppCompatSpinner languageSpinner;
    private Show currentShow;
    private MediaPlayerTrack currentTrack;

    private String mMediaId;
    private int screenHeight;
    private int fromY;
    private int toY;
    private int originalPosition = 0;
    private int channelID = 1;
    //    private boolean isPlayerExpanded = false;
    private static final int ANIMATION_SPEED = 700;
    private MainPresenter mainPresenter;

    private MediaBrowserCompat.ConnectionCallback mConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    Log.d(TAG, "onConnected: session token " + mMediaBrowser.getSessionToken());

                    if (mMediaId == null) {
                        mMediaId = mMediaBrowser.getRoot();
                    }
                    mMediaBrowser.subscribe(mMediaId, mSubscriptionCallback);
                    try {
                        MediaControllerCompat mediaController =
                                new MediaControllerCompat(MainActivity.this,
                                        mMediaBrowser.getSessionToken());
                        MediaControllerCompat.setMediaController(MainActivity.this, mediaController);

                        // Register a Callback to stay in sync
                        mediaController.registerCallback(mControllerCallback);
                    } catch (RemoteException e) {
                        Log.e(TAG, "Failed to connect to MediaController", e);
                    }
                }

                @Override
                public void onConnectionFailed() {
                    Log.e(TAG, "onConnectionFailed");
                }

                @Override
                public void onConnectionSuspended() {
                    Log.d(TAG, "onConnectionSuspended");
                    MediaControllerCompat mediaController = MediaControllerCompat
                            .getMediaController(MainActivity.this);
                    if (mediaController != null) {
                        mediaController.unregisterCallback(mControllerCallback);
                        MediaControllerCompat.setMediaController(MainActivity.this, null);
                    }
                }
            };
    private MediaControllerCompat.Callback mControllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {
                    if (metadata != null) {
                        Log.d(TAG, metadata.getDescription().getTitle().toString());
                        musicProvider.setCurrentMediaMetadata(metadata);

                    }
                }

                @Override
                public void onPlaybackStateChanged(PlaybackStateCompat state) {
                    musicProvider.setPlaybackState(state);
                    eventBus.post(new MediaPlayerEvent());
                    Log.d(TAG, state.getState() + " .. callback ..");
                }
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (eventBus != null)
//            eventBus.post(new MediaPlayerEvent());
//    }

    @SuppressLint("Recycle")
    private void init() {
        ImageView closeImage = findViewById(R.id.close_image_view);
        ImageView expandPlayer = findViewById(R.id.expand_image_view);
        CustomRecyclerView menuRecyclerView = findViewById(R.id.side_menu_recycler_view);
        megaImage = findViewById(R.id.mega_image_view);
        naghamImageView = findViewById(R.id.nagham_image_view);
        radioHitsImageView = findViewById(R.id.radio_image_view);
        sh3byImageView = findViewById(R.id.sh3by_image_view);
        playerFragment = findViewById(R.id.player_fragment_container);
        footerPlayer = findViewById(R.id.footer_player_layout);
        playerProgressBar = findViewById(R.id.player_progress_bar);
        exploreMenu = findViewById(R.id.explore_text_view);
        loginImageView = findViewById(R.id.menu_item_image);
        loginTextView = findViewById(R.id.menu_item_title);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        userLogLayout = findViewById(R.id.menu_item_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationManager = new NavigationManager(this);
        musicProvider = MusicProvider.getInstance();
        eventBus = EventBus.getDefault();

        mediaControlButton = findViewById(R.id.media_control_btn);
        showTitle = findViewById(R.id.show_title);
        showTimes = findViewById(R.id.show_times);
        menuCollapsedImage = findViewById(R.id.menu_collapsed_image);
        menuOpenedLayout = findViewById(R.id.menu_view);

        TypedArray typedArray = getResources().obtainTypedArray(R.array.menu_icons);
        MenuAdapter menuAdapter = new MenuAdapter(this, typedArray);
        menuAdapter.setOnItemClicked(this);
        menuRecyclerView.setAdapter(menuAdapter);

        menuCollapsedImage.setOnClickListener(this);
        mediaControlButton.setOnClickListener(this);
        megaImage.setOnClickListener(this);
        naghamImageView.setOnClickListener(this);
        radioHitsImageView.setOnClickListener(this);
        sh3byImageView.setOnClickListener(this);
        exploreMenu.setOnClickListener(this);
        closeImage.setOnClickListener(this);
        expandPlayer.setOnClickListener(this);
        userLogLayout.setOnClickListener(this);

        showTitle.setText(String.format(getString(R.string.enjoy_listening), getString(R.string.radio_hits)));
        showTitle.setTextColor(ContextCompat.getColor(this, ChannelUtils.getChannelSecondaryColor(Constants.RADIO_HITS_ID)));

        if (PrefUtils.getLoginProvider(this) == LoginProviders.NONE.getLoginProviderCode()) {
            loginImageView.setImageResource(R.drawable.ic_login_sm);
            loginTextView.setText(R.string.login);
        } else {
            loginImageView.setImageResource(R.drawable.ic_login_sm);
            loginImageView.setRotationX(180);
            loginTextView.setText(R.string.logout);
        }

        languageSpinner = findViewById(R.id.language_spinner);
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.language_single_item, getResources().getStringArray(R.array.languages));
        languageSpinner.setAdapter(spinnerAdapter);

        if (PrefUtils.getAppLang(this).equals(PrefUtils.ENGLISH_LANG)) {
            languageSpinner.setSelection(0, false);
        } else {
            languageSpinner.setSelection(1, false);
        }

        languageSpinner.setOnItemSelectedListener(this);
        mainPresenter = new MainPresenterImpl(this);
        initMediaBrowser();
        initAnimation();
        openExploreFragment();
        openPlayerFragment();
        if (getIntent() != null && getIntent().hasExtra(Constants.EXTRA_SHOW_ID)) {
            int id = getIntent().getIntExtra(Constants.EXTRA_SHOW_ID, 0);
            int channelID = getIntent().getIntExtra(Constants.EXTRA_CHANNEL_ID, 0);
            ShowsClickEvent showsClickEvent = new ShowsClickEvent(id, channelID, true);
            onShowClicked(showsClickEvent);
        }
    }

    private void checkToLogoutOrLogin() {
        mainPresenter.checkToLogoutOrLogin();
    }

    private void openExploreFragment() {
        if (!(navigationManager.getCurrentFragment() instanceof ExploreFragment)) {
            ExploreFragment exploreFragment = new ExploreFragment();
            exploreFragment.setChannelNavigationInterface(this);
            exploreFragment.setOnMenuOpenClicked(this);
            navigationManager.openFragmentAsRoot(exploreFragment, R.id.fragment_container, ExploreFragment.class.getSimpleName());
        }
    }

    private void openPlayerFragment() {
        PlayerFragment playerFragment = new PlayerFragment();
        playerFragment.setOnPlayerCollapsed(this::hidePlayer);
        playerFragment.setChannelNavigationInterface(this);
        navigationManager.openFragmentAsRoot(playerFragment, R.id.player_fragment_container, ExploreFragment.class.getSimpleName());
    }

    @Override
    public void openChannel(int channelID) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRA_CHANNEL_ID, channelID);
        ChannelDetailsFragment channelDetailsFragment = new ChannelDetailsFragment();
        channelDetailsFragment.setArguments(bundle);
        navigationManager.openFragment(channelDetailsFragment, R.id.fragment_container, ChannelDetailsFragment.class.getSimpleName());
        updateMenu(channelID);
        hideMenu();
    }

    @Override
    public void openSearchFragment() {
        SearchFragment searchFragment = new SearchFragment();
        navigationManager.openFragment(searchFragment, R.id.fragment_container, SearchFragment.class.getSimpleName());
    }

    private void initMediaBrowser() {
        mMediaBrowser =
                new MediaBrowserCompat(this, new ComponentName(this, MusicService.class), mConnectionCallback, null);

    }


    private MediaBrowserCompat.SubscriptionCallback mSubscriptionCallback =
            new MediaBrowserCompat.SubscriptionCallback() {
                @Override
                public void onChildrenLoaded(String parentId,
                                             List<MediaBrowserCompat.MediaItem> children) {
                }

                @Override
                public void onError(String id) {
                }
            };


    @Override
    public void onLiveShowsLoaded() {
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowPlayClicked(Show show) {
        this.currentShow = show;
        showTimes.setText(TextUtils.getScheduleTimes(show.getSchedules()));
        showTitle.setText(show.getTitle());
        showTimes.setVisibility(View.VISIBLE);

        channelID = show.getChannel().getId();
        showTitle.setTextColor(ContextCompat.getColor(this, ChannelUtils.getChannelSecondaryColor(channelID)));

        controlPlayer(channelID, false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowPlayClicked(MediaPlayerTrack mediaPlayerTrack) {
        currentTrack = mediaPlayerTrack;
        showTitle.setText(mediaPlayerTrack.getMediaTitle());
        channelID = mediaPlayerTrack.getMediaChannelID();

        if (mediaPlayerTrack.getSchedules() != null && !mediaPlayerTrack.getSchedules().isEmpty()) {
            showTimes.setText(TextUtils.getScheduleTimes(mediaPlayerTrack.getSchedules()));
            showTimes.setVisibility(View.VISIBLE);
        } else {
            showTimes.setVisibility(View.GONE);
        }

        showTitle.setTextColor(ContextCompat.getColor(this, ChannelUtils.getChannelSecondaryColor(channelID)));

        controlPlayer(channelID, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMediaBrowser.connect();
        if (!eventBus.isRegistered(this))
            eventBus.register(this);
    }


    @Override
    protected void onStop() {
        mMediaBrowser.disconnect();
        if (eventBus.isRegistered(this))
            eventBus.unregister(this);
        super.onStop();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BaseFragment baseFragment = navigationManager.getCurrentFragment();
        if (baseFragment.getArguments() != null && baseFragment.getArguments().containsKey(Constants.EXTRA_CHANNEL_ID))
            updateMenu(baseFragment.getArguments().getInt(Constants.EXTRA_CHANNEL_ID));
        else updateMenu(0);
    }

    @Override
    protected void onDestroy() {
        Log.d("MainActivity", "ONDESTROY");
        if (musicProvider.getPlayingMediaId() != null)
            controlPlayer(Integer.parseInt(musicProvider.getPlayingMediaId()), true);
        super.onDestroy();
    }

    private void controlPlayer(int channelID, boolean stop) {
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(this);
        MediaControllerCompat.TransportControls controls = controller.getTransportControls();
        if (stop) {
            controls.stop();
        } else {
            footerPlayer.setVisibility(View.VISIBLE);
            boolean isPlaying = String.valueOf(channelID).equals(musicProvider.getPlayingMediaId());
            int state = PlaybackStateCompat.STATE_PAUSED;
            if (isPlaying && musicProvider.getmPlaybackState() != null)
                state = musicProvider.getmPlaybackState().getState();

            switch (state) {
                case PlaybackStateCompat.STATE_PLAYING:
                    controls.pause();
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    controls.playFromMediaId(String.valueOf(channelID), null);
                    break;
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MediaPlayerEvent mediaPlayerEvent) {
        updatePlayBtnStatus();
    }


    private void updatePlayBtnStatus() {
        if (musicProvider.getPlayingMediaId() != null && musicProvider.getPlayingMediaId().equals(String.valueOf(channelID))) {
            int state = musicProvider.getmPlaybackState().getState();
            switch (state) {
                case PlaybackStateCompat.STATE_BUFFERING:
                    showPlayerProgress();
                    setPlayBtn();
                    break;
                case PlaybackStateCompat.STATE_PLAYING:
                    hidePlayerProgress();
                    setPauseBtn();
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    hidePlayerProgress();
                    setPlayBtn();
                    break;
                case PlaybackStateCompat.STATE_ERROR:
                    hidePlayerProgress();
                    setPlayBtn();
                    Toast.makeText(this, R.string.streaming_error, Toast.LENGTH_LONG).show();
                    break;
            }
        } else {
            setPlayBtn();
        }
    }

    private void setPlayBtn() {
        mediaControlButton.setImageResource(R.drawable.ic_play_btn);
    }

    private void setPauseBtn() {
        mediaControlButton.setImageResource(R.drawable.ic_pause_btn);
    }

    private void showPlayerProgress() {
        playerProgressBar.setVisibility(View.VISIBLE);
    }

    private void hidePlayerProgress() {
        playerProgressBar.setVisibility(View.GONE);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPresenterClicked(PresenterClickEvent presenterClickEvent) {
        if (presenterClickEvent.isNavigate()) {
            PresenterDetailsFragment presenterDetailsFragment = new PresenterDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.EXTRA_PRESENTER_ID, presenterClickEvent.getPresenterID());
            bundle.putInt(Constants.EXTRA_CHANNEL_ID, presenterClickEvent.getChannelID());
            presenterDetailsFragment.setArguments(bundle);
            navigationManager.openFragment(presenterDetailsFragment, R.id.fragment_container, PresenterDetailsFragment.class.getSimpleName());
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowClicked(ShowsClickEvent showsClickEvent) {
        if (showsClickEvent.isNavigate()) {
            ShowDetailsFragment showDetailsFragment = new ShowDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.EXTRA_SHOW_ID, showsClickEvent.getShowID());
            bundle.putInt(Constants.EXTRA_CHANNEL_ID, showsClickEvent.getChannelID());
            showDetailsFragment.setArguments(bundle);
            navigationManager.openFragment(showDetailsFragment, R.id.fragment_container, ShowDetailsFragment.class.getSimpleName());
        }
    }

    private void showMenu() {
        ObjectAnimator animTranslate = ObjectAnimator.ofFloat(menuOpenedLayout, "translationY", fromY, 0);
        animTranslate.setDuration(ANIMATION_SPEED);
        animTranslate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                menuOpenedLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                menuOpenedLayout.setClickable(false);
//                close.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animTranslate.start();
    }

    private void hideMenu() {
        ObjectAnimator animTranslate = ObjectAnimator.ofFloat(menuOpenedLayout, "translationY", 0, toY);

        animTranslate.setDuration(ANIMATION_SPEED);
        animTranslate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                menuOpenedLayout.setVisibility(View.GONE);
                menuCollapsedImage.setClickable(true);
//                close.setClickable(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animTranslate.start();
    }

    private void showPlayer() {
        ObjectAnimator animTranslate = ObjectAnimator.ofFloat(playerFragment,
                "translationY", screenHeight, 0);
        animTranslate.setDuration(ANIMATION_SPEED);
        animTranslate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                playerFragment.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                isPlayerExpanded = true;
//                menuOpenedLayout.setClickable(false);
//                close.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animTranslate.start();
    }

    @Override
    public void hidePlayer() {
        ObjectAnimator animTranslate = ObjectAnimator.ofFloat(playerFragment, "translationY", 0, toY);
        animTranslate.setDuration(ANIMATION_SPEED);
        animTranslate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                menuOpenedLayout.setVisibility(View.GONE);
                menuCollapsedImage.setClickable(true);
//                close.setClickable(false);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animTranslate.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_image_view:
                hideMenu();
                break;
            case R.id.menu_collapsed_image:
                showMenu();
                break;
            case R.id.media_control_btn:
//                showPlayerProgress();
                if (currentShow != null)
                    controlPlayer(currentShow.getChannel().getId(), false);
                else if (currentTrack != null)
                    controlPlayer(currentTrack.getMediaChannelID(), false);
//                else
//                    onShowPlayClicked(new MediaPlayerTrack(Constants.RADIO_HITS_ID,
//                            getString(R.string.enjoy_listening, getString(ChannelUtils.getChannelTitle(Constants.RADIO_HITS_ID)))));
                break;
            case R.id.mega_image_view:
                openChannel(Constants.MEGA_FM_ID);
                break;
            case R.id.radio_image_view:
                openChannel(Constants.RADIO_HITS_ID);
                break;
            case R.id.nagham_image_view:
                openChannel(Constants.NAGHAM_ID);
                break;
            case R.id.sh3by_image_view:
                openChannel(Constants.SH3BY_ID);
                break;
            case R.id.explore_text_view:
                openExploreFragment();
                break;
            case R.id.expand_image_view:
                showPlayer();
                break;
            case R.id.menu_image_view:
                if (mDrawerLayout.isDrawerOpen(Gravity.START))
                    mDrawerLayout.closeDrawer(Gravity.START);
                else
                    mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.menu_item_layout:
                checkToLogoutOrLogin();
                break;
        }
    }

    private void initAnimation() {
        int playerHeight = footerPlayer.getLayoutParams().height;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.y;
        fromY = toY = screenHeight - playerHeight;
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onItemClicked(int position) {
        switch (position) {

            case 0:
                openExploreFragment();
                mDrawerLayout.closeDrawer(Gravity.START);
                break;
            case 1:
                AboutNrcFragment aboutNrcFragment = new AboutNrcFragment();
                aboutNrcFragment.setOnMenuOpenClicked(this);
                navigationManager.openFragment(aboutNrcFragment, R.id.fragment_container, AboutNrcFragment.class.getSimpleName());
                mDrawerLayout.closeDrawer(Gravity.START);
                break;
            case 2:
                if (PrefUtils.getLoginProvider(this) != LoginProviders.NONE.getLoginProviderCode()) {
                    ProfileFragment profileFragment = new ProfileFragment();
//                    aboutNrcFragment.setOnMenuOpenClicked(this);
                    navigationManager.openFragment(profileFragment, R.id.fragment_container, AboutNrcFragment.class.getSimpleName());
                    mDrawerLayout.closeDrawer(Gravity.START);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.user_not_login_msg), Toast.LENGTH_LONG).show();
                }
                break;

            case 3:

                NewsFragment newsFragment = new NewsFragment();
                newsFragment.setOnMenuOpenClicked(this);
                navigationManager.openFragment(newsFragment, R.id.fragment_container, NewsFragment.class.getSimpleName());
                mDrawerLayout.closeDrawer(Gravity.START);
                break;
            case 4:
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                contactUsFragment.setOnMenuOpenClicked(this);
                navigationManager.openFragment(contactUsFragment, R.id.fragment_container, ContactUsFragment.class.getSimpleName());
                mDrawerLayout.closeDrawer(Gravity.START);
                break;
            case 5:
                languageSpinner.performClick();
                break;
        }
    }

    @Override
    public void onMenuImageClicked() {
        mDrawerLayout.openDrawer(Gravity.START);
    }

    private void updateMenu(int channelID) {
        switch (channelID) {
            case Constants.NAGHAM_ID:
                naghamImageView.setAlpha(1f);
                megaImage.setAlpha(0.5f);
                radioHitsImageView.setAlpha(0.5f);
                sh3byImageView.setAlpha(0.5f);
                exploreMenu.setAlpha(0.5f);
                break;
            case Constants.MEGA_FM_ID:
                naghamImageView.setAlpha(0.5f);
                megaImage.setAlpha(1f);
                radioHitsImageView.setAlpha(0.5f);
                sh3byImageView.setAlpha(0.5f);
                exploreMenu.setAlpha(0.5f);
                break;
            case Constants.RADIO_HITS_ID:
                naghamImageView.setAlpha(0.5f);
                megaImage.setAlpha(0.5f);
                radioHitsImageView.setAlpha(1f);
                sh3byImageView.setAlpha(0.5f);
                exploreMenu.setAlpha(0.5f);
                break;
            case Constants.SH3BY_ID:
                naghamImageView.setAlpha(0.5f);
                megaImage.setAlpha(0.5f);
                radioHitsImageView.setAlpha(0.5f);
                sh3byImageView.setAlpha(1f);
                exploreMenu.setAlpha(0.5f);
                break;
            default:
                naghamImageView.setAlpha(0.5f);
                megaImage.setAlpha(0.5f);
                radioHitsImageView.setAlpha(0.5f);
                sh3byImageView.setAlpha(0.5f);
                exploreMenu.setAlpha(1f);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 && !PrefUtils.getAppLang(getApplicationContext()).equals(PrefUtils.ENGLISH_LANG))
            changeLanguage(PrefUtils.ENGLISH_LANG);

        if (position == 1 && !PrefUtils.getAppLang(getApplicationContext()).equals(PrefUtils.ARABIC_LANG))
            changeLanguage(PrefUtils.ARABIC_LANG);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressWarnings("deprecation")
    public void changeLanguage(String lang) {
        Locale myLocale = new Locale(lang);
        Configuration conf = new Configuration();
        conf.locale = myLocale;

        getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
        if (lang.equals(PrefUtils.ARABIC_LANG))
            PrefUtils.setAppLang(this, PrefUtils.ARABIC_LANG);
        else
            PrefUtils.setAppLang(this, PrefUtils.ENGLISH_LANG);

        PrefUtils.setIsLanguageSelected(this, true);
        restartActivity();
    }

    public void restartActivity() {
        startActivity(getLaunchIntent(this));
        finish();
    }
}
