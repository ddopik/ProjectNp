package com.spade.nrc.ui.explore.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spade.nrc.R;
import com.spade.nrc.ui.shows.model.Presenter;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.utils.Constants;
import com.spade.nrc.utils.GlideApp;

import java.util.List;

import static com.spade.nrc.utils.Constants.FEATURED_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.LIVE_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.NORMAL_SHOW_TYPE;
import static com.spade.nrc.utils.Constants.SCHEDULE_SHOW_TYPE;

/**
 * Created by Ayman Abouzeid on 1/6/18.
 */

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ShowsViewHolder> {

    private Context context;
    private List<Show> showList;
    private int itemViewType, channelColor;

    public ShowsAdapter(Context context, List<Show> showList, int itemViewType, int channelColor) {
        this.context = context;
        this.showList = showList;
        this.itemViewType = itemViewType;
        this.channelColor = channelColor;
    }

    @Override
    public ShowsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (itemViewType == NORMAL_SHOW_TYPE) {
            view = layoutInflater.inflate(R.layout.item_show, parent, false);
        } else if (itemViewType == SCHEDULE_SHOW_TYPE) {
            view = layoutInflater.inflate(R.layout.item_show_schedule, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.item_explore_show, parent, false);
        }
        return new ShowsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowsViewHolder holder, int position) {
        Show show = showList.get(position);

        switch (itemViewType) {
            case FEATURED_SHOW_TYPE:
                holder.showTime.setVisibility(View.GONE);
                holder.statusImage.setVisibility(View.GONE);
                holder.channelImage.setVisibility(View.VISIBLE);
                break;
            case LIVE_SHOW_TYPE:
                String showTimeString = show.getSchedules().get(0).getStartsAt().concat(" - ").concat(show.getSchedules().get(0).getEndsAt());
                holder.showTime.setText(showTimeString);
                holder.showTime.setVisibility(View.VISIBLE);
                holder.statusImage.setVisibility(View.VISIBLE);
                holder.channelImage.setVisibility(View.VISIBLE);
                break;
            case SCHEDULE_SHOW_TYPE:
                String timeString = show.getSchedules().get(0).getStartsAt().concat(" - ").concat(show.getSchedules().get(0).getEndsAt());
                holder.showTime.setText(timeString);
                holder.showTime.setVisibility(View.VISIBLE);
                holder.showName.setTextColor(channelColor);
                break;
        }

        GlideApp.with(context).load(show.getMedia()).into(holder.showImage);
        holder.showName.setText(show.getTitle());

        String presenterName = "";
        for (Presenter presenter : show.getPresenters()) {
            presenterName = presenterName.concat(presenter.getName());
        }
        holder.presenterName.setText(presenterName);
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
            showName = itemView.findViewById(R.id.show_name);
            presenterName = itemView.findViewById(R.id.presenter_name);
            showImage = itemView.findViewById(R.id.show_image);
            if (itemViewType == Constants.LIVE_SHOW_TYPE || itemViewType == Constants.FEATURED_SHOW_TYPE) {
                channelImage = itemView.findViewById(R.id.channel_image);
                statusImage = itemView.findViewById(R.id.status_image);
            }

            if (itemViewType != Constants.NORMAL_SHOW_TYPE)
                showTime = itemView.findViewById(R.id.show_times);

        }
    }
}
