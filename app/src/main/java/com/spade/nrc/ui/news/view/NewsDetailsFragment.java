package com.spade.nrc.ui.news.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.general.NavigationManager;
import com.spade.nrc.ui.news.model.News;
import com.spade.nrc.ui.news.model.NewsInner;
import com.spade.nrc.ui.news.presenter.NewsDetailsPresenter;
import com.spade.nrc.ui.news.presenter.NewsDetailsPresenterImp;
import com.spade.nrc.utils.ChannelUtils;
import com.spade.nrc.utils.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailsFragment extends BaseFragment implements NewsDetailsView {


    private View mainView;
    private NewsDetailsPresenter newsDetailsPresenter;
    private ProgressBar NewsDetailprogressBar;
    private ProgressBar relatedNewsProgressBar;
    private RelativeLayout newsSection;
    private RelativeLayout relatedNewsSection;
    private ImageView newsImage;
    private TextView newsTitle;
    private TextView newsTime;
    private TextView newsDescription;
    private TextView newsHeadTitle;


    private RelatedNewsAdapter relatedNewsAdapter;
    private RecyclerView newsRecyclerView;
    private List<NewsInner> newsList = new ArrayList<>();
    private String newsID;
    private int newItemViewType = 0;  //-->horizontal

    private NavigationManager.OnMenuOpenClicked onMenuOpenClicked;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        newsID = this.getArguments().getString("news_id");
        mainView = inflater.inflate(R.layout.fragment_news_details, container, false);
        initViews();
        return mainView;
    }

    @Override
    protected void initViews() {
        ImageView menuImageView = mainView.findViewById(R.id.menu_image_view);
        newsSection = mainView.findViewById(R.id.news_section);
        relatedNewsSection = mainView.findViewById(R.id.related_news_section);
        newsImage = mainView.findViewById(R.id.news_image);
        newsTitle = mainView.findViewById(R.id.news_title);
        newsHeadTitle = mainView.findViewById(R.id.news_head_title);
        newsTime = mainView.findViewById(R.id.news_time);
        newsDescription = mainView.findViewById(R.id.news_description);
        NewsDetailprogressBar = mainView.findViewById(R.id.progress_bar);
        relatedNewsProgressBar = mainView.findViewById(R.id.related_news_progress_bar);
        newsRecyclerView = mainView.findViewById(R.id.related_new_recycler);
        relatedNewsAdapter = new RelatedNewsAdapter(getActivity(), newsList, newItemViewType);
        newsRecyclerView.setAdapter(relatedNewsAdapter);
        menuImageView.setOnClickListener(view -> onMenuOpenClicked.onMenuImageClicked());
        newsDetailsPresenter.getNewsDetail(newsID);
        newsDetailsPresenter.getRelatedNews(newsID);
    }

    public void setOnMenuOpenClicked(NavigationManager.OnMenuOpenClicked onMenuOpenClicked) {
        this.onMenuOpenClicked = onMenuOpenClicked;
    }


    @Override
    protected void initPresenter() {
        newsDetailsPresenter = new NewsDetailsPresenterImp(this, getActivity());
    }


    @Override
    public void showProgressBar() {
        NewsDetailprogressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        NewsDetailprogressBar.setVisibility(View.GONE);
    }

    @Override
    public void viewRelatedNews(List<NewsInner> newsList) {
        relatedNewsSection.setVisibility(View.VISIBLE);
        this.newsList.clear();
        this.newsList.addAll(newsList);
        relatedNewsAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewNewsDetailsInnerImage(String imgUrl, RequestOptions requestOptions) {
        newsImage.setVisibility(View.VISIBLE);
        GlideApp.with(getActivity()).load(imgUrl).apply(requestOptions).centerCrop()
                .placeholder(ChannelUtils.getShowDefaultImage(R.drawable.ic_news_listing_default)).apply(requestOptions)
                .into(newsImage);

    }

    @Override
    public void viewNewsDetailsInnerTitle(String title) {
        newsTitle.setVisibility(View.VISIBLE);
        newsTitle.setText(title);
        newsTitle.setText(title);
    }

    @Override
    public void viewNewsDetailsInnerDate(String date) {
        newsTime.setVisibility(View.VISIBLE);
        newsTime.setText(getResources().getString(R.string.static_published_at) + " " + date);
    }

    @Override
    public void viewNewsDetailsInnerDescription(String description) {
        newsDescription.setVisibility(View.VISIBLE);
        newsDescription.setText(description);
    }



    @Override
    public void hideRelatedNews() {
        relatedNewsSection.setVisibility(View.GONE);
    }

    @Override
    public void showRelatedNewsLoading() {
        relatedNewsProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRelatedNewsLoading() {
        relatedNewsProgressBar.setVisibility(View.GONE);
    }
}
