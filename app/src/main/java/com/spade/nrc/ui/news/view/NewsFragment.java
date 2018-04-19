package com.spade.nrc.ui.news.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.CustomViews.CustomRecyclerView;
import com.spade.nrc.ui.general.NavigationManager;
import com.spade.nrc.ui.main.MainActivity;
import com.spade.nrc.ui.news.model.News;
import com.spade.nrc.ui.news.presenter.NewsPresenter;
import com.spade.nrc.ui.news.presenter.NewsPresenterImp;


import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends BaseFragment implements NewsFragmentView, NewsAdapter.NewsActions {


    private NewsPresenter newsPresenter;
    private View mainView;
    private ProgressBar progressBar;
    private CustomRecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;
    private List<News> newsList = new ArrayList<>();
    private NavigationManager navigationManager;
    private int itemType = 2; //-->Grid

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_news, container, false);
        navigationManager=new NavigationManager((MainActivity)getActivity());
        initViews();
        return mainView;
    }

    @Override
    protected void initViews() {
        ImageView menuImageView = mainView.findViewById(R.id.menu_image_view);
        newsRecyclerView = mainView.findViewById(R.id.news_recycler_view);
        newsAdapter = new NewsAdapter(getActivity(), newsList, itemType);
        progressBar = mainView.findViewById(R.id.progress_bar);
        menuImageView.setOnClickListener(view -> onMenuOpenClicked.onMenuImageClicked());
        newsAdapter.setNewsActions(this);



        newsRecyclerView.setAdapter(newsAdapter);
        newsPresenter.getNews();
    }

    private NavigationManager.OnMenuOpenClicked onMenuOpenClicked;

    @Override
    protected void initPresenter() {
        newsPresenter = new NewsPresenterImp(getActivity());
        newsPresenter.setView(this);
        sendAnalytics();
    }


    @Override
    public void viewNews(List<News> newsList) {
        this.newsList.clear();
        this.newsList.addAll(newsList);
        newsAdapter.notifyDataSetChanged();

    }

    @Override
    public void hideNews() {
        newsRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void viewStateMessage(String messgae) {
        super.showToastMessage(messgae
        );
    }


    private void sendAnalytics() {
        Tracker causesTracker = NRCApplication.getDefaultTracker();
        causesTracker.setScreenName(getContext().getString(R.string.news_nrc_analytics));
        causesTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void setOnMenuOpenClicked(NavigationManager.OnMenuOpenClicked onMenuOpenClicked) {
        this.onMenuOpenClicked = onMenuOpenClicked;
    }



    @Override
    public void onNewsClicked(News news) {
        NewsDetailsFragment newsDetailsFragment=new NewsDetailsFragment();
        newsDetailsFragment.setOnMenuOpenClicked((MainActivity) getActivity());
        Bundle bundle = new Bundle();
        bundle.putString("news_id",news.getId().toString());
        newsDetailsFragment.setArguments(bundle);
        navigationManager.openFragment(newsDetailsFragment, R.id.fragment_container, NewsDetailsFragment.class.getSimpleName());

    }

}
