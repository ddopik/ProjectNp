package com.spade.nrc.ui.channel.view;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.channel.presenter.AboutChannelPresenter;
import com.spade.nrc.ui.channel.presenter.AboutChannelPresenterImpl;
import com.spade.nrc.ui.shows.model.Channel;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.Utils;

/**
 * Created by Ayman Abouzeid on 1/26/18.
 */

public class AboutChannelFragment extends BaseFragment implements AboutChannelView, View.OnClickListener {

    private View aboutChannelView;
    private AboutChannelPresenter aboutChannelPresenter;
    private ImageView channelImage;
    private TextView aboutChannel;
    private ProgressBar progressBar;
    private String youtubeUrl, instagramUrl, facebookUrl, twitterUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        aboutChannelView = inflater.inflate(R.layout.fragment_about_channel, container, false);
        initViews();
        return aboutChannelView;
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
        aboutChannelPresenter = new AboutChannelPresenterImpl();
        aboutChannelPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        progressBar = aboutChannelView.findViewById(R.id.progress_bar);
        aboutChannel = aboutChannelView.findViewById(R.id.about_channel_text_view);
        channelImage = aboutChannelView.findViewById(R.id.channel_image);
        ImageView facebookImage = aboutChannelView.findViewById(R.id.facebook_image_view);
        ImageView twitterImage = aboutChannelView.findViewById(R.id.twitter_image_view);
        ImageView instagramImage = aboutChannelView.findViewById(R.id.instagram_image_view);
        ImageView youtubeImage = aboutChannelView.findViewById(R.id.youtube_image_view);
        facebookImage.setOnClickListener(this);
        twitterImage.setOnClickListener(this);
        instagramImage.setOnClickListener(this);
        youtubeImage.setOnClickListener(this);
        int channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources()
                .getColor(ChannelUtils.getChannelSecondaryColor(channelID)), PorterDuff.Mode.SRC_IN);
        aboutChannelPresenter.getChannelInfo(channelID);
    }


    @Override
    public void showChannelData(Channel channel) {
        facebookUrl = channel.getFacebook();
        twitterUrl = channel.getTwitter();
        instagramUrl = channel.getInstgram();
        youtubeUrl = channel.getYoutube();

        aboutChannel.setText(channel.getBrief());
        GlideApp.with(getContext())
                .load(channel.getMedia()).into(channelImage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.facebook_image_view:
                Utils.openURL(facebookUrl, getContext());
                break;
            case R.id.twitter_image_view:
                Utils.openURL(twitterUrl, getContext());
                break;
            case R.id.instagram_image_view:
                Utils.openURL(instagramUrl, getContext());
                break;
            case R.id.youtube_image_view:
                Utils.openURL(youtubeUrl, getContext());
                break;
        }
    }
}
