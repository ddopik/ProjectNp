package com.spade.nrc.ui.player;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.media.player.MediaPlayerTrack;
import com.spade.nrc.nrc.media.player.MediaPlayerEvent;
import com.spade.nrc.nrc.media.player.MusicProvider;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.TextUtils;
import com.spade.nrc.utils.Utils;
import com.vansuita.gaussianblur.GaussianBlur;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 1/27/18.
 */

public class PlayerFragment extends BaseFragment implements PlayerView, View.OnClickListener {

    private int TRANSLATION_VALUE;
    private View playerView;
    private FrameLayout nextShowLayout;
    private LiveShowImagesAdapter liveShowImagesAdapter;
    private ViewPager viewPager;
    private ImageView channelLogo, showImageBackground, mediaControlBtn, nextShowImage, nextShowBackground;
    private TextView showTitle, showPresentersNames, nextShowTimes, nextShowName, nextShowPresenters, upNextShow, showDescription;
    private ProgressBar progressBar, playerProgressBar;
    private String mediaTitle, facebookUrl, twitterUrl, phoneNumber, smsNumber;
    private PlayerPresenter playerPresenter;
    private MusicProvider musicProvider;
    private EventBus eventBus;
    private OnPlayerCollapsed onPlayerCollapsed;
    private Show currentShow;

    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, String> channelsShowsImages = new HashMap<>();
    private List<String> showsImages = new ArrayList<>();
    private int pagePosition = 0;
    private int channelID = Constants.RADIO_HITS_ID;
    private boolean isExpanded = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        playerView = inflater.inflate(R.layout.fragment_player, container, false);
        initViews();
        return playerView;
    }

    @Override
    protected void initPresenter() {
        playerPresenter = new PlayerPresenterImpl(getContext());
        playerPresenter.setView(this);
        musicProvider = MusicProvider.getInstance();
        eventBus = EventBus.getDefault();
    }

    @Override
    protected void initViews() {
        ImageView facebookImage = playerView.findViewById(R.id.facebook_image_view);
        ImageView twitterImage = playerView.findViewById(R.id.twitter_image_view);
        ImageView smsImage = playerView.findViewById(R.id.sms_image_view);
        ImageView callImage = playerView.findViewById(R.id.call_image_view);
        ImageView collapseImage = playerView.findViewById(R.id.collapse_image);
        nextShowBackground = playerView.findViewById(R.id.next_show_background);
        upNextShow = playerView.findViewById(R.id.up_next_text_view);
        nextShowLayout = playerView.findViewById(R.id.next_show_layout);
        nextShowImage = playerView.findViewById(R.id.show_image);
        nextShowName = playerView.findViewWithTag(getString(R.string.next_show_title_tag));
        nextShowTimes = playerView.findViewById(R.id.show_times);
        showDescription = playerView.findViewById(R.id.show_description);
        nextShowPresenters = playerView.findViewById(R.id.presenter_name);
        viewPager = playerView.findViewById(R.id.live_shows_images_pager);
        mediaControlBtn = playerView.findViewById(R.id.media_control_btn);
        showTitle = playerView.findViewById(R.id.show_title);
        channelLogo = playerView.findViewById(R.id.channel_logo);
        showPresentersNames = playerView.findViewById(R.id.presenter_names);
        progressBar = playerView.findViewById(R.id.progress_bar);
        playerProgressBar = playerView.findViewById(R.id.player_progress_bar);
        showImageBackground = playerView.findViewById(R.id.player_background);

        loadChannels();

        liveShowImagesAdapter = new LiveShowImagesAdapter(showsImages, getContext());
        viewPager.setAdapter(liveShowImagesAdapter);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(150, 0, 150, 0);
        viewPager.setPageMargin(36);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pagePosition = position;
                channelID = ChannelUtils.getChannelID(position);
                playerPresenter.getLiveStreamingShows(String.valueOf(channelID));
                updatePlayBtnStatus();
                getChannelData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        playerPresenter.getLiveStreamingShows(String.valueOf(channelID));
        viewPager.setCurrentItem(0);
        mediaControlBtn.setOnClickListener(this);
        facebookImage.setOnClickListener(this);
        twitterImage.setOnClickListener(this);
        callImage.setOnClickListener(this);
        smsImage.setOnClickListener(this);
        collapseImage.setOnClickListener(this);
        upNextShow.setOnClickListener(this);

        TRANSLATION_VALUE = convertDpToPixels(132);
        hideNextShowLayout();
    }

    public int convertDpToPixels(float dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
        return px;
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
        setCurrentPage();
        updatePlayBtnStatus();
    }

    private void setCurrentPage() {
        int playingMedia = 0;
        if (musicProvider.getPlayingMediaId() != null)
            playingMedia = Integer.parseInt(musicProvider.getPlayingMediaId());
        pagePosition = ChannelUtils.getChannelPosition(playingMedia);
        viewPager.setCurrentItem(pagePosition, true);
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
    public void showCurrentShow(Show currentShow) {
        this.currentShow = currentShow;
        String mediaURL;
        if (currentShow != null) {
            mediaTitle = currentShow.getTitle();
            showPresentersNames.setText(TextUtils.getPresentersNames(currentShow.getPresenters()));
            showDescription.setText(currentShow.getDescription());
            showPresentersNames.setVisibility(View.VISIBLE);
            showDescription.setVisibility(View.VISIBLE);
            mediaURL = currentShow.getMedia();

        } else {
            mediaTitle = String.format(getString(R.string.enjoy_listening)
                    , getString(ChannelUtils.getChannelTitle(channelID)));
            showPresentersNames.setVisibility(View.GONE);
            showDescription.setVisibility(View.GONE);
            mediaURL = "";
        }

        GlideApp.with(getContext()).asBitmap().listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e,
                                        Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                Bitmap blurredBitmap = GaussianBlur.with(getContext()).render(resource);
                showImageBackground.setImageBitmap(blurredBitmap);
                return true;
            }
        }).load(mediaURL).placeholder(ChannelUtils.getPlayerBackground(channelID)).into(showImageBackground);

        channelsShowsImages.put(channelID, mediaURL);
        loadImages();
        showTitle.setText(mediaTitle);
        channelLogo.setImageResource(ChannelUtils.getChannelMenuLogo(channelID));
    }

    @Override
    public void showNextShow(Show nextShow) {
        if (nextShow != null) {
            nextShowLayout.setVisibility(View.VISIBLE);
            facebookUrl = nextShow.getChannel().getFacebook();
            twitterUrl = nextShow.getChannel().getTwitter();
            smsNumber = nextShow.getChannel().getSmsNumber();
            phoneNumber = nextShow.getChannel().getTelephoneNumber();

            nextShowTimes.setTextColor(ContextCompat.getColor(getContext(), ChannelUtils.getChannelSecondaryColor(channelID)));
            nextShowName.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            nextShowPresenters.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

            nextShowTimes.setText(TextUtils.getScheduleTimes(nextShow.getSchedules()));
            nextShowName.setText(nextShow.getTitle());
            nextShowPresenters.setText(TextUtils.getPresentersNames(nextShow.getPresenters()));
            GlideApp.with(getContext()).load(nextShow.getMedia()).centerCrop()
                    .placeholder(ChannelUtils.getShowDefaultImage(channelID))
                    .into(nextShowImage);
        } else {
            nextShowLayout.setVisibility(View.GONE);
        }
    }

    private void loadImages() {
        showsImages.clear();
        showsImages.add(channelsShowsImages.get(Constants.RADIO_HITS_ID));
        showsImages.add(channelsShowsImages.get(Constants.MEGA_FM_ID));
        showsImages.add(channelsShowsImages.get(Constants.NAGHAM_ID));
        showsImages.add(channelsShowsImages.get(Constants.SH3BY_ID));
        liveShowImagesAdapter.notifyDataSetChanged();

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
                    showToastMessage(R.string.streaming_error);
                    break;
            }
        } else {
            hidePlayerProgress();
            setPlayBtn();
        }
    }

    private void showPlayerProgress() {
        playerProgressBar.setVisibility(View.VISIBLE);
    }

    private void setPauseBtn() {
        mediaControlBtn.setImageResource(R.drawable.ic_pause_btn);

    }

    private void setPlayBtn() {
        mediaControlBtn.setImageResource(R.drawable.ic_play_btn);
    }

    private void hidePlayerProgress() {
        playerProgressBar.setVisibility(View.GONE);
    }


    private void loadChannels() {
        channelsShowsImages.put(Constants.RADIO_HITS_ID, "");
        channelsShowsImages.put(Constants.MEGA_FM_ID, "");
        channelsShowsImages.put(Constants.NAGHAM_ID, "");
        channelsShowsImages.put(Constants.SH3BY_ID, "");
        this.showsImages.clear();
        this.showsImages.addAll(channelsShowsImages.values());
    }


    void getChannelData() {
        ChannelUtils channelUtils = ChannelUtils.getInstance();
        channelUtils.getChannelById(channelID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(channel -> {
                    facebookUrl = channel.getFacebook();
                    twitterUrl = channel.getTwitter();
                    phoneNumber = channel.getTelephoneNumber();
                    smsNumber = channel.getSmsNumber();
                });
    }

    private void showNextShowLayout() {
        isExpanded = true;
        nextShowLayout.animate().translationYBy(-1 * TRANSLATION_VALUE).setDuration(700)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        upNextShow.setEnabled(false);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_down);
                        upNextShow.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                        upNextShow.setEnabled(true);
                        nextShowBackground.setAlpha(0.9f);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    private void hideNextShowLayout() {
        isExpanded = false;
        nextShowLayout.animate().translationYBy(TRANSLATION_VALUE).setDuration(700)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        upNextShow.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_arrow_up);
                        upNextShow.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                        upNextShow.setEnabled(true);
                        nextShowBackground.setAlpha(0.3f);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.media_control_btn:
                if (currentShow != null)
                    eventBus.post(currentShow);
                else
                    eventBus.post(new MediaPlayerTrack(channelID, mediaTitle, null));
                break;
            case R.id.facebook_image_view:
                if (facebookUrl != null && !facebookUrl.isEmpty())
                    Utils.openURL(facebookUrl, getContext());
                break;
            case R.id.twitter_image_view:
                if (twitterUrl != null && !twitterUrl.isEmpty())
                    Utils.openURL(twitterUrl, getContext());
                break;
            case R.id.sms_image_view:
                if (smsNumber != null && !smsNumber.isEmpty())
                    Utils.sendSms(smsNumber, getContext());
                break;
            case R.id.call_image_view:
                if (phoneNumber != null && !phoneNumber.isEmpty())
                    Utils.dial(phoneNumber, getContext());
                break;
            case R.id.collapse_image:
                onPlayerCollapsed.onCollapseClicked();
                break;
            case R.id.up_next_text_view:
                if (isExpanded)
                    hideNextShowLayout();
                else
                    showNextShowLayout();
        }
    }

    public void setOnPlayerCollapsed(OnPlayerCollapsed onPlayerCollapsed) {
        this.onPlayerCollapsed = onPlayerCollapsed;
    }

    public interface OnPlayerCollapsed {
        void onCollapseClicked();
    }
}
