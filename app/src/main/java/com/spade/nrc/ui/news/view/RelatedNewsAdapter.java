package com.spade.nrc.ui.news.view;

import android.content.Context;
import android.support.annotation.NonNull;
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

import com.spade.nrc.ui.news.model.NewsInner;
import com.spade.nrc.ui.player.LiveShowImagesAdapter;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.GlideApp;

import java.util.List;

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.NewsViewHolder> {

    private List<NewsInner> newsList;
    private int itemViewType;
    private Context context;
    private NewsActions newsActions;

    public RelatedNewsAdapter(Context context, List<NewsInner> newsList, int itemViewType) {
        this.context = context;
        this.newsList = newsList;
        this.itemViewType = itemViewType;
    }



    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (itemViewType == 1) {
            view = layoutInflater.inflate(R.layout.item_search_news, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.item_news, parent, false);
        }

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(LiveShowImagesAdapter.CORNERS_RADIUS));
        NewsInner news = newsList.get(position);
        if (newsList.get(position) != null) {
            holder.newsTitle.setText(news.getTitle());
            holder.news_times.setText(news.getCreatedAt());
            holder.newsID.setText(news.getId().toString());
            GlideApp.with(context).load(news.getMedia()).apply(requestOptions).centerCrop()
                    .placeholder(ChannelUtils.getShowDefaultImage(R.drawable.ic_news_listing_default)).apply(requestOptions)
                    .into(holder.newsImage);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsActions.onNewsClicked(news);
            }
        });


    }



    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public interface NewsActions {
        void onNewsClicked(NewsInner news);
    }

    public void setNewsActions(NewsActions newsActions) {
        this.newsActions = newsActions;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView newsTitle;
        public ImageView newsImage;
        public TextView news_times;
        public TextView newsID;

        public NewsViewHolder(View view) {
            super(view);
            newsTitle = view.findViewById(R.id.news_title);
            newsImage = view.findViewById(R.id.news_image);
            news_times = view.findViewById(R.id.news_times);
        }
    }

}
