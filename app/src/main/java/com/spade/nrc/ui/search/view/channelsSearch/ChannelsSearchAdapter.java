package com.spade.nrc.ui.search.view.channelsSearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.spade.nrc.R;
import com.spade.nrc.ui.shows.model.Channel;
import com.spade.nrc.utils.ChannelUtils;

import java.util.List;

/**
 * Created by 20128 on 4/5/2018.
 */

public class ChannelsSearchAdapter extends RecyclerView.Adapter<ChannelsSearchAdapter.ChannelSearchViewHolder> {

    private Context context;
    private List<Channel> channelsList;

    public ChannelsSearchAdapter(Context context, List<Channel> channelsList) {
        this.context = context;
        this.channelsList = channelsList;
    }

    @NonNull
    @Override
    public ChannelSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.channel_image_grid_item, parent, false);
        return new ChannelSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelSearchViewHolder holder, int position) {
        holder.channelImageView.setImageResource(ChannelUtils.getChannelAboutImage(channelsList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return channelsList.size();
    }

    class ChannelSearchViewHolder extends RecyclerView.ViewHolder {
        ImageView channelImageView;

        ChannelSearchViewHolder(View view) {
            super(view);
            channelImageView = itemView.findViewById(R.id.channel_image);
        }

    }
}
