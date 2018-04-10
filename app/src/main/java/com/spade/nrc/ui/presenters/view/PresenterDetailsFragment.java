package com.spade.nrc.ui.presenters.view;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.event.bus.events.ShowsClickEvent;
import com.spade.nrc.ui.explore.view.ShowsAdapter;
import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.ui.presenters.presenter.PresenterDetailsPresenter;
import com.spade.nrc.ui.presenters.presenter.PresenterDetailsPresenterImpl;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.Utils;
import com.vansuita.gaussianblur.GaussianBlur;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/26/18.
 */

public class PresenterDetailsFragment extends BaseFragment implements PresenterDetailsView, View.OnClickListener, ShowsAdapter.ShowActions {

    private View presenterDetailsView;
    private PresenterDetailsPresenter presenterDetailsPresenter;
    private ShowsAdapter showsAdapter;
    private LinearLayout showLayout;
    private ImageView presenterImage;
    private ImageView presenterBlurredImage;
    private TextView presenterName, aboutPresenter;
    private ProgressBar progressBar;
    private List<Show> shows = new ArrayList<>();

    private String facebookUrl = "", twitterUrl = "", instagramUrl = "";
    private int channelID;
    private EventBus eventBus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenterDetailsView = inflater.inflate(R.layout.fragment_presenter_details_coordinator, container, false);
        initViews();
        return presenterDetailsView;
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
    public void showPresenterData(Presenter presenter) {
        instagramUrl = presenter.getInstagram();
        facebookUrl = presenter.getFacebook();
        twitterUrl = presenter.getTwitter();
        presenterName.setText(presenter.getName());
        aboutPresenter.setText(presenter.getDescription());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CircleCrop());


        GlideApp.with(getContext())
                .load(presenter.getMedia()).apply(requestOptions)
                .placeholder(ChannelUtils.getPresenterDefaultImageWhite(channelID)).into(presenterImage);

        GlideApp.with(getContext()).asBitmap().listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e,
                                        Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                Bitmap blurredBitmap = GaussianBlur.with(getContext()).render(resource);
                presenterBlurredImage.setImageBitmap(blurredBitmap);
                return true;
            }
        }).load(presenter.getMedia()).into(presenterBlurredImage);

        shows.addAll(presenter.getShowList());
        showsAdapter.notifyDataSetChanged();
        if (shows.isEmpty())
            showLayout.setVisibility(View.GONE);

        sendAnalytics(presenter.getName());
    }

    @Override
    public void updateAddToFavouriteBtn() {
        showsAdapter.notifyDataSetChanged();
    }

    private void sendAnalytics(String screenName) {
        Tracker causesTracker = NRCApplication.getDefaultTracker();
        causesTracker.setScreenName(screenName);
        causesTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void initPresenter() {
        presenterDetailsPresenter = new PresenterDetailsPresenterImpl(getContext());
        presenterDetailsPresenter.setView(this);
        eventBus = EventBus.getDefault();
    }

    @Override
    protected void initViews() {
        CustomRecyclerView showsRecyclerView = presenterDetailsView.findViewById(R.id.presenter_shows_recycler_view);
        Toolbar toolbar = presenterDetailsView.findViewById(R.id.toolbar);
        ImageView backBtn = toolbar.findViewById(R.id.back_button);
        ImageView facebookImage = presenterDetailsView.findViewById(R.id.facebook_image_view);
        ImageView twitterImage = presenterDetailsView.findViewById(R.id.twitter_image_view);
        ImageView instagramImage = presenterDetailsView.findViewById(R.id.instagram_image_view);
        ImageView presenterImageBackground = presenterDetailsView.findViewById(R.id.presenter_image_background);
        showLayout = presenterDetailsView.findViewById(R.id.show_layout);
        progressBar = presenterDetailsView.findViewById(R.id.progress_bar);
        presenterName = presenterDetailsView.findViewById(R.id.presenter_name);
        aboutPresenter = presenterDetailsView.findViewById(R.id.about_presenter_text_view);
        presenterImage = presenterDetailsView.findViewById(R.id.presenter_image);
        presenterBlurredImage = presenterDetailsView.findViewById(R.id.presenter_blurred_image);
        showsRecyclerView.setNestedScrollingEnabled(false);

        facebookImage.setOnClickListener(this);
        twitterImage.setOnClickListener(this);
        instagramImage.setOnClickListener(this);

        channelID = getArguments().getInt(Constants.EXTRA_CHANNEL_ID);

        progressBar.getIndeterminateDrawable().setColorFilter(getResources()
                .getColor(ChannelUtils.getChannelSecondaryColor(channelID)), PorterDuff.Mode.SRC_IN);

        int presenterID = getArguments().getInt(Constants.EXTRA_PRESENTER_ID);

        presenterBlurredImage.
                setBackgroundColor(ContextCompat.getColor(getContext(), ChannelUtils.getChannelSecondaryColor(channelID)));
        presenterImageBackground.
                setImageResource(ChannelUtils.getPresenterImageBackground(channelID));

        showsAdapter = new ShowsAdapter(getContext(),
                shows, Constants.SCHEDULE_SHOW_TYPE);
        showsAdapter.setShowActions(this);
        showsRecyclerView.setAdapter(showsAdapter);
        presenterDetailsPresenter.getPresenterDetails(String.valueOf(presenterID), String.valueOf(channelID));
        backBtn.setOnClickListener(view -> getActivity().onBackPressed());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.facebook_image_view:
                if (facebookUrl != null)
                    Utils.openURL(facebookUrl, getContext());
                break;
            case R.id.twitter_image_view:
                if (twitterUrl != null)
                    Utils.openURL(twitterUrl, getContext());
                break;
            case R.id.instagram_image_view:
                if (instagramUrl != null)
                    Utils.openURL(instagramUrl, getContext());
                break;
        }
    }

    @Override
    public void onShowClicked(Show show) {
        eventBus.post(new ShowsClickEvent(show.getId(), show.getChannel().getId(), true));

    }

    @Override
    public void onFavClicked(int showID) {
        presenterDetailsPresenter.addShowToFav(showID);
    }
}
