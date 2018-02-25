package com.spade.nrc.ui.explore.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.R;
import com.spade.nrc.nrc.media.player.MusicProvider;
import com.spade.nrc.ui.explore.model.LiveShowsData;
import com.spade.nrc.ui.shows.model.Channel;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.TextUtils;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/6/18.
 */

public class LiveShowsAdapter extends RecyclerView.Adapter<LiveShowsAdapter.ShowsViewHolder> {

    private Context context;
    private List<LiveShowsData> showList;
    private MusicProvider musicProvider;
    private LiveShowActions showActions;

    public LiveShowsAdapter(Context context, List<LiveShowsData> showList) {
        this.context = context;
        this.showList = showList;
        musicProvider = MusicProvider.getInstance();
    }

    @Override
    public ShowsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.item_explore_show, parent, false);
        return new ShowsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowsViewHolder holder, int position) {
        LiveShowsData showData = showList.get(position);
        Show show = showData.getShow();
        Channel channel = showData.getChannel();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new RoundedCorners(32));

        RequestOptions option = new RequestOptions().centerCrop().optionalTransform(new RoundedCorners(10));


        String mediaUrl;
        if (show != null) {
            String showTimeString = TextUtils.getScheduleTimes(show.getSchedules());
            holder.showTime.setText(showTimeString);
            holder.showTime.setVisibility(View.VISIBLE);
            holder.showName.setText(show.getTitle());
            holder.presenterName.setText(TextUtils.getPresentersNames(show.getPresenters()));
            mediaUrl = show.getMedia();
        } else {
            mediaUrl = "";
            holder.showTime.setVisibility(View.GONE);
            holder.presenterName.setVisibility(View.GONE);
            String mediaTitle = String.format(context.getString(R.string.enjoy_listening)
                    , context.getString(ChannelUtils.getChannelTitle(channel.getId())));
            holder.showName.setText(mediaTitle);
        }

        holder.statusImage.setVisibility(View.VISIBLE);
        holder.channelImage.setVisibility(View.VISIBLE);
        holder.channelImage.setImageResource(ChannelUtils.getChannelImage(channel.getId()));
        GlideApp.with(context).load(mediaUrl).centerCrop()
                .placeholder(ChannelUtils.getShowDefaultImage(channel.getId())).apply(option)
                .into(holder.showImage);

        if (musicProvider.getPlayingMediaId() != null && musicProvider.getPlayingMediaId().equals(String.valueOf(channel.getId()))) {
            int state = musicProvider.getmPlaybackState().getState();
            switch (state) {
                case PlaybackStateCompat.STATE_BUFFERING:
                    holder.statusImage.setImageResource(R.drawable.ic_playbtn_landing);

                    break;
                case PlaybackStateCompat.STATE_PLAYING:
                    holder.statusImage.setImageResource(R.drawable.ic_live_streaming_landing);
                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                    holder.statusImage.setImageResource(R.drawable.ic_playbtn_landing);

                    break;
                case PlaybackStateCompat.STATE_ERROR:
                    holder.statusImage.setImageResource(R.drawable.ic_playbtn_landing);
                    break;
            }
        } else {
            holder.statusImage.setImageResource(R.drawable.ic_playbtn_landing);

        }

        holder.statusImage.setOnClickListener(view -> {
            if (showActions != null)
                showActions.onPlayClicked(show, channel.getId());
        });


        holder.showImage.setBackgroundColor(ContextCompat.getColor(context,
                ChannelUtils.getChannelSecondaryColor(channel.getId())));
        holder.itemView.setOnClickListener(view -> {
            if (showActions != null && show != null)
                showActions.onLiveShowClicked(show, channel.getId());
        });
    }

    public interface LiveShowActions {
        void onLiveShowClicked(Show show, int channelID);

        void onPlayClicked(Show show, int channelID);
    }

    public void setShowActions(LiveShowActions showActions) {
        this.showActions = showActions;
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }


    class ShowsViewHolder extends RecyclerView.ViewHolder {

        private TextView showName, presenterName, showTime;
        private ImageView channelImage, statusImage, showImage;

        ShowsViewHolder(View itemView) {
            super(itemView);
            showName = itemView.findViewById(R.id.show_title);
            presenterName = itemView.findViewById(R.id.presenter_name);
            showImage = itemView.findViewById(R.id.show_image);
            channelImage = itemView.findViewById(R.id.channel_image);
            statusImage = itemView.findViewById(R.id.status_image);
            showTime = itemView.findViewById(R.id.show_times);

        }
    }
}
