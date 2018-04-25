package com.spade.nrc.ui.channel.view;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.media.player.MediaPlayerTrack;
import com.spade.nrc.nrc.media.player.MediaPlayerEvent;
import com.spade.nrc.nrc.media.player.MusicProvider;
import com.spade.nrc.ui.channel.presenter.LiveStreamingPresenter;
import com.spade.nrc.ui.channel.presenter.LiveStreamingPresenterImpl;
import com.spade.nrc.ui.event.bus.events.ShowsClickEvent;
import com.spade.nrc.ui.player.LiveShowImagesAdapter;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.TextUtils;
import com.spade.nrc.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 1/31/18.
 */

public class LiveStreamingFragment extends BaseFragment implements LiveStreamingView, View.OnClickListener {

    private LiveStreamingPresenter liveStreamingPresenter;
    private View liveStreamingView;
    private ImageView currentShowImage, nextShowImage;
    private TextView currentShowName, currentShowPresenters, nextShowName, nextShowPresenters, nextShowTimes, upNextText;
    private ImageView mediaControlBtn;
    private ImageView currentShowFavImageView;
    private ImageView nextShowFavImageView;
    private ProgressBar progressBar, playerProgressBar;
    private MusicProvider musicProvider;
    private EventBus eventBus;
    private Show currentShow, nextShow;
    private int channelID;
    private String mediaTitle, facebookUrl, twitterUrl, telephoneNumber, smsNumber;
    private RelativeLayout nextShowLayout, favouriteLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        liveStreamingView = inflater.inflate(R.layout.fragment_live_streaming, container, false);
        initViews();
        return liveStreamingView;
    }

    @Override
    protected void initPresenter() {
        liveStreamingPresenter = new LiveStreamingPresenterImpl(getContext());
        liveStreamingPresenter.setView(this);
        musicProvider = MusicProvider.getInstance();
        eventBus = EventBus.getDefault();
    }

    @Override
    protected void initViews() {
        ImageView facebookImage = liveStreamingView.findViewById(R.id.facebook_image_view);
        ImageView twitterImage = liveStreamingView.findViewById(R.id.twitter_image_view);
        ImageView smsImage = liveStreamingView.findViewById(R.id.sms_image_view);
        ImageView callImage = liveStreamingView.findViewById(R.id.call_image_view);
        ImageView shareImageView = liveStreamingView.findViewById(R.id.share_image_view);

        favouriteLayout = liveStreamingView.findViewById(R.id.favourite_share_layout);
        currentShowFavImageView = liveStreamingView.findViewById(R.id.fav_image_view);
        nextShowFavImageView = liveStreamingView.findViewById(R.id.favourite_image_btn);
        nextShowLayout = liveStreamingView.findViewById(R.id.item_show);
        progressBar = liveStreamingView.findViewById(R.id.progress_bar);
        currentShowImage = liveStreamingView.findViewById(R.id.current_show_image);
        currentShowName = liveStreamingView.findViewById(R.id.current_show_title);
        currentShowPresenters = liveStreamingView.findViewById(R.id.current_show_presenter_name);
        nextShowImage = liveStreamingView.findViewById(R.id.show_image);
        nextShowName = liveStreamingView.findViewById(R.id.show_title);
        nextShowTimes = liveStreamingView.findViewById(R.id.show_times);
        nextShowPresenters = liveStreamingView.findViewById(R.id.presenter_name);
        mediaControlBtn = liveStreamingView.findViewById(R.id.media_control_btn);
        upNextText = liveStreamingView.findViewById(R.id.up_next_text);
        playerProgressBar = liveStreamingView.findViewById(R.id.player_progress_bar);
        ViewCompat.setElevation(playerProgressBar, getContext().getResources().getDimension(R.dimen.activity_horizontal_margin));
        channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        liveStreamingPresenter.getLiveStreamingShows(String.valueOf(channelID));


        progressBar.getIndeterminateDrawable().setColorFilter(getResources()
                .getColor(ChannelUtils.getChannelSecondaryColor(channelID)), PorterDuff.Mode.SRC_IN);
        playerProgressBar.getIndeterminateDrawable().setColorFilter(getResources()
                .getColor(ChannelUtils.getChannelSecondaryColor(channelID)), PorterDuff.Mode.SRC_IN);
        currentShowImage.setImageResource(ChannelUtils.getLiveStreamingDefault(channelID));
        ViewCompat.setElevation(mediaControlBtn, getResources().getDimension(R.dimen.activity_horizontal_margin));
        updatePlayBtnStatus();

        mediaControlBtn.setOnClickListener(this);
        facebookImage.setOnClickListener(this);
        twitterImage.setOnClickListener(this);
        smsImage.setOnClickListener(this);
        callImage.setOnClickListener(this);
        nextShowLayout.setOnClickListener(this);
        currentShowFavImageView.setOnClickListener(this);
        nextShowFavImageView.setOnClickListener(this);
        shareImageView.setImageResource(ChannelUtils.getChannelShareBtn(channelID));
        getChannelContactInfo();
        sendAnalytics(String.format(getString(R.string.channel_live_streaming_analytics), getString(ChannelUtils.getChannelTitle(channelID))));
    }

    private void sendAnalytics(String screenName) {
        Tracker causesTracker = NRCApplication.getDefaultTracker();
        causesTracker.setScreenName(screenName);
        causesTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void getChannelContactInfo() {
        ChannelUtils channelUtils = ChannelUtils.getInstance();
        channelUtils.getChannelById(channelID).observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(channel -> {
                    facebookUrl = channel.getFacebook();
                    twitterUrl = channel.getTwitter();
                    smsNumber = channel.getSmsNumber();
                    telephoneNumber = channel.getTelephoneNumber();
                });
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
    public void showCurrentShow(Show currentShow) {
        if (currentShow != null) {
            this.currentShow = currentShow;
            currentShowName.setText(currentShow.getTitle());
            if (currentShow.getPresenters() != null && !currentShow.getPresenters().isEmpty())
                currentShowPresenters.setText(TextUtils.getPresentersNames(currentShow.getPresenters()));
            else
                currentShowPresenters.setVisibility(View.GONE);
            GlideApp.with(getContext()).load(currentShow.getMedia()).centerCrop()
                    .placeholder(ChannelUtils.getLiveStreamingDefault(channelID)).centerCrop()
                    .into(currentShowImage);
        } else {
            favouriteLayout.setVisibility(View.GONE);
            mediaTitle = String.format(getString(R.string.enjoy_listening)
                    , getString(ChannelUtils.getChannelTitle(channelID)));
            currentShowName.setText(mediaTitle);
            currentShowPresenters.setVisibility(View.GONE);
            currentShowImage.setImageResource(ChannelUtils.getLiveStreamingDefault(channelID));
        }
    }

    @Override
    public void showNextShow(Show nextShow) {
        if (nextShow != null) {
            upNextText.setVisibility(View.VISIBLE);
            this.nextShow = nextShow;
            nextShowTimes.setTextColor(ContextCompat.getColor(getContext(), ChannelUtils.getChannelSecondaryColor(channelID)));
            nextShowTimes.setText(TextUtils.getScheduleTimes(nextShow.getSchedules()));
            nextShowName.setText(nextShow.getTitle());
            nextShowPresenters.setText(TextUtils.getPresentersNames(nextShow.getPresenters()));
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(LiveShowImagesAdapter.CORNERS_RADIUS));
            GlideApp.with(getContext()).load(nextShow.getMedia()).apply(requestOptions).centerCrop()
                    .placeholder(ChannelUtils.getShowDefaultImage(channelID)).apply(requestOptions)
                    .into(nextShowImage);
        } else {
            nextShowLayout.setVisibility(View.GONE);
            upNextText.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateFavBtn() {
        int addedFavBtn = ChannelUtils.getChannelFavAddedBtn(channelID);
        int favBtn = ChannelUtils.getChannelFavBtn(channelID);

        if (currentShow != null)
            if (liveStreamingPresenter.isLiked(currentShow.getId())) {
                currentShowFavImageView.setImageResource(addedFavBtn);
            } else {
                currentShowFavImageView.setImageResource(favBtn);
            }
        else
            currentShowFavImageView.setVisibility(View.GONE);


        if (nextShow != null)
            if (liveStreamingPresenter.isLiked(nextShow.getId())) {
                nextShowFavImageView.setImageResource(addedFavBtn);
            } else {
                nextShowFavImageView.setImageResource(favBtn);
            }
    }


    private void updatePlayBtnStatus() {
        if (musicProvider.getPlayingMediaId() != null && musicProvider.getPlayingMediaId().equals(String.valueOf(channelID))) {
            int state = musicProvider.getmPlaybackState().getState();
            switch (state) {
                case PlaybackStateCompat.STATE_BUFFERING:
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
            setPlayBtn();
        }
    }

    private void setPlayBtn() {
        mediaControlBtn.setImageResource(ChannelUtils.getChannelPlayBtn(channelID));
    }

    private void setPauseBtn() {
        mediaControlBtn.setImageResource(ChannelUtils.getChannelPauseBtn(channelID));
    }

    private void showPlayerProgress() {
//        playerProgressBar.setVisibility(View.VISIBLE);
    }

    private void hidePlayerProgress() {
        playerProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.media_control_btn:
                showPlayerProgress();
                if (currentShow == null) {
                    eventBus.post(new MediaPlayerTrack(channelID, mediaTitle, null));
                } else {
                    eventBus.post(currentShow);
                }
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
                if (telephoneNumber != null && !telephoneNumber.isEmpty())
                    Utils.dial(telephoneNumber, getContext());
                break;
            case R.id.item_show:
                if (nextShow != null)
                    eventBus.post(new ShowsClickEvent(nextShow.getId(), nextShow.getChannel().getId(), true));
                break;
            case R.id.fav_image_view:
                if (currentShow != null) {
                    liveStreamingPresenter.addShowToFav(currentShow.getId());
                }
                break;
            case R.id.favourite_image:
                if (nextShow != null) {
                    liveStreamingPresenter.addShowToFav(nextShow.getId());
                }
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
        updatePlayBtnStatus();
    }

}
