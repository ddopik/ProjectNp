package com.spade.nrc.ui.explore.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
import com.spade.nrc.ui.player.LiveShowImagesAdapter;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.TextUtils;

import java.util.List;

import static com.spade.nrc.utils.Constants.FEATURED_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.LIVE_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.NORMAL_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.SCHEDULE_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.SEARCH_SHOW_TYPE;

/**
 * Created by Ayman Abouzeid on 1/6/18.
 */

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ShowsViewHolder> {

    private Context context;
    private List<Show> showList;
    private int itemViewType;
    private ShowActions showActions;
    private RealmDbHelper realmDbHelper;

    public ShowsAdapter(Context context, List<Show> showList, int itemViewType) {
        this.context = context;
        this.showList = showList;
        this.itemViewType = itemViewType;
        realmDbHelper = new RealmDbImpl();
    }

    @NonNull
    @Override
    public ShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
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
    }

    @Override
    public void onBindViewHolder(@NonNull ShowsViewHolder holder, int position) {
        Show show = showList.get(position);
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
        return showList.size();
    }

    class ShowsViewHolder extends RecyclerView.ViewHolder {

        private TextView showName, presenterName, showTime;
        private ImageView channelImage, statusImage, showImage,favImage;

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

            if (itemViewType == NORMAL_SHOW_TYPE || itemViewType == SCHEDULE_SHOW_TYPE)
                favImage = itemView.findViewById(R.id.favourite_image);

        }
    }
}
