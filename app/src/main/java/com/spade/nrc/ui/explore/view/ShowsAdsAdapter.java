package com.spade.nrc.ui.explore.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.R;
import com.spade.nrc.realm.RealmDbHelper;
import com.spade.nrc.realm.RealmDbImpl;
import com.spade.nrc.ui.ads.AdModel;
import com.spade.nrc.ui.player.LiveShowImagesAdapter;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static com.spade.nrc.utils.Constants.FEATURED_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.LIVE_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.NORMAL_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.SCHEDULE_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.SEARCH_SHOW_TYPE;

/**
 * Created by Ayman Abouzeid on 1/6/18.
 */

public class ShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int SHOW_VIEW_TYPE = 1;
    private static final int AD_VIEW_TYPE = 2;
    private Context context;
    private List<Show> showList;
    private List<AdModel> adModelList = new ArrayList<>();
    private int itemViewType;
    private ShowActions showActions;
    private RealmDbHelper realmDbHelper;
    private final int adFactor = 3;
    private int incrementValue = 0;
    private int addAds = 0;
    private int value;

    public ShowsAdapter(Context context, List<Show> showList, int itemViewType) {
        this.context = context;
        this.showList = showList;
        this.itemViewType = itemViewType;
        realmDbHelper = new RealmDbImpl();
    }

    public ShowsAdapter(Context context, List<Show> showList, int itemViewType, List<AdModel> adModels) {
        this.context = context;
        this.showList = showList;
        this.itemViewType = itemViewType;
        this.adModelList = adModels;
        realmDbHelper = new RealmDbImpl();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (viewType == SHOW_VIEW_TYPE) {
            if (itemViewType == NORMAL_SHOW_TYPE) {
                view = layoutInflater.inflate(R.layout.item_show, parent, false);
            } else if (itemViewType == SCHEDULE_SHOW_TYPE) {
                view = layoutInflater.inflate(R.layout.item_show_schedule, parent, false);
            } else if (itemViewType == SEARCH_SHOW_TYPE) {
                view = layoutInflater.inflate(R.layout.item_search_show, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.item_explore_show, parent, false);
            }
            return new ShowsViewHolder(view);
        } else {
            return new AdsViewHolder(LayoutInflater
                    .from(parent.getContext()).inflate(R.layout.item_ad, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ShowsViewHolder) {
            ShowsViewHolder holder = (ShowsViewHolder) viewHolder;
            int showPosition = (position - addAds);
            Show show = showList.get(showPosition);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(LiveShowImagesAdapter.CORNERS_RADIUS));
            boolean isFaved = realmDbHelper.isShowLiked(show.getId());
            switch (itemViewType) {
                case FEATURED_SHOW_TYPE:
                case SEARCH_SHOW_TYPE:
                    holder.showTime.setVisibility(View.GONE);
                    holder.statusImage.setVisibility(View.GONE);
                    holder.channelImage.setVisibility(View.VISIBLE);
                    holder.channelImage.setImageResource(ChannelUtils.getChannelImage(show.getChannel().getId()));
                    break;
                case SCHEDULE_SHOW_TYPE:
                    String timeString = TextUtils.getScheduleTimes(show.getSchedules());
                    holder.showTime.setText(timeString);
                    holder.showTime.setVisibility(View.VISIBLE);
                    holder.showTime.setTextColor(ContextCompat.getColor(context, ChannelUtils.getChannelSecondaryColor(show.getChannel().getId())));
                    holder.showName.setTextColor(ContextCompat.getColor(context, ChannelUtils.getChannelPrimaryColor(show.getChannel().getId())));
                    holder.favImage.setOnClickListener(view -> {
                        if (showActions != null)
                            showActions.onFavClicked(show.getId());
                    });
                    holder.favImage.setVisibility(View.VISIBLE);
                    if (isFaved)
                        holder.favImage.setImageResource(ChannelUtils.getChannelFavAddedBtn(show.getChannel().getId()));
                    else
                        holder.favImage.setImageResource(ChannelUtils.getChannelFavBtn(show.getChannel().getId()));
                    break;
                case NORMAL_SHOW_TYPE:
                    holder.favImage.setOnClickListener(view -> {
                        if (showActions != null)
                            showActions.onFavClicked(show.getId());
                    });
                    holder.favImage.setVisibility(View.VISIBLE);
                    if (isFaved)
                        holder.favImage.setImageResource(ChannelUtils.getChannelFavAddedBtn(0));
                    else
                        holder.favImage.setImageResource(ChannelUtils.getChannelFavBtn(0));
                    break;
            }

            if (itemViewType != NORMAL_SHOW_TYPE)
                GlideApp.with(context).load(show.getMedia()).apply(requestOptions).centerCrop()
                        .placeholder(ChannelUtils.getShowDefaultImage(show.getChannel().getId())).apply(requestOptions)
                        .into(holder.showImage);
            else
                GlideApp.with(context).load(show.getMedia()).centerCrop()
                        .placeholder(ChannelUtils.getShowDefaultImage(show.getChannel().getId()))
                        .into(holder.showImage);

            holder.showName.setText(show.getTitle());
            holder.presenterName.setText(TextUtils.getPresentersNames(show.getPresenters()));
            holder.itemView.setOnClickListener(view -> {
                if (showActions != null)
                    showActions.onShowClicked(show);
            });

        } else {
            if (adModelList != null && !adModelList.isEmpty()) {
                AdsViewHolder vHolder = (AdsViewHolder) viewHolder;
//                (position / adFactor) - 1
                AdModel adModel = adModelList.get((position / adFactor) - 1);
                GlideApp.with(context).load(adModel.getMedia()).centerCrop()
                        .into(vHolder.adImage);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        boolean moreBanners = position / adFactor <= adModelList.size();
        if (position % adFactor == incrementValue && position % 2 != 0 && moreBanners && position != 0) {
            if (incrementValue == 2) {
                incrementValue = 0;
            } else {
                incrementValue += 1;
            }
            addAds += 1;
            Log.d("AD view type", position + " .. " + String.valueOf(addAds));
            return AD_VIEW_TYPE;
        } else {
            Log.d("SHOW view type", position + " .. " + String.valueOf(addAds));
            return SHOW_VIEW_TYPE;
        }

    }


    public interface ShowActions {
        void onShowClicked(Show show);

        void onFavClicked(int showID);
    }

    public void setShowActions(ShowActions showActions) {
        this.showActions = showActions;
    }

    @Override
    public int getItemCount() {
//        int adsSize = adModelList.size();
//        int mDataSetSize = showList.size();
//        int expectedSize = mDataSetSize / adFactor;
//        if (adsSize <= expectedSize) {
//            Log.d("Count as expected", String.valueOf(mDataSetSize + adsSize));
//            return mDataSetSize + adsSize;
//        } else {
//            Log.d("Count as not expected", String.valueOf(mDataSetSize + expectedSize));
//            return mDataSetSize + expectedSize;
//        }
        return 11;
    }


    class AdsViewHolder extends RecyclerView.ViewHolder {

        private ImageView adImage;

        AdsViewHolder(View itemView) {
            super(itemView);
            adImage = itemView.findViewById(R.id.ad_image);
        }
    }

    class ShowsViewHolder extends RecyclerView.ViewHolder {

        private TextView showName, presenterName, showTime;
        private ImageView channelImage, statusImage, showImage, favImage;

        ShowsViewHolder(View itemView) {
            super(itemView);
            showName = itemView.findViewById(R.id.show_title);
            presenterName = itemView.findViewById(R.id.presenter_name);
            showImage = itemView.findViewById(R.id.show_image);
            if (itemViewType == LIVE_SHOW_TYPE || itemViewType == FEATURED_SHOW_TYPE || itemViewType == SEARCH_SHOW_TYPE) {
                channelImage = itemView.findViewById(R.id.channel_image);
                statusImage = itemView.findViewById(R.id.status_image);
            }

            if (itemViewType != NORMAL_SHOW_TYPE)
                showTime = itemView.findViewById(R.id.show_times);

            if (itemViewType == NORMAL_SHOW_TYPE || itemViewType == SCHEDULE_SHOW_TYPE)
                favImage = itemView.findViewById(R.id.favourite_image);

        }
    }
}
