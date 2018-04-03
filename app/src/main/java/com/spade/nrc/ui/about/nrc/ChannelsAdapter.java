package com.spade.nrc.ui.about.nrc;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.spade.nrc.R;


/**
 * Created by Ayman Abouzeid on 10/30/17.
 */

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.MenuViewHolder> {
    private Context mContext;
    private TypedArray typedArray;
//    private OnItemClicked onItemClicked;

    public ChannelsAdapter(Context mContext, TypedArray typedArray) {
        this.mContext = mContext;
        this.typedArray = typedArray;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.channel_image_grid_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, final int position) {
        holder.channelImageView.setImageResource(typedArray.getResourceId(position, 0));
//        holder.itemView.setOnClickListener(v -> onItemClicked.onItemClicked(position));
    }

    @Override
    public int getItemCount() {
        return typedArray.length();
    }


    class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView channelImageView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            channelImageView = itemView.findViewById(R.id.channel_image);
        }
    }
}
