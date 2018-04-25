package com.spade.nrc.ui.search.view.NewsSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.base.BaseSearchFragment;
import com.spade.nrc.ui.news.model.News;
import com.spade.nrc.ui.news.presenter.NewsPresenter;
import com.spade.nrc.ui.news.presenter.NewsPresenterImp;
import com.spade.nrc.ui.news.view.NewsAdapter;
import com.spade.nrc.ui.search.presenter.NewsPresenter.NewsSearchPresenter;
import com.spade.nrc.ui.search.presenter.NewsPresenter.NewsSearchPresenterImp;

import java.util.ArrayList;
import java.util.List;

public class NewsSearchFragment extends BaseSearchFragment implements NewsSearchFragmentView, NewsAdapter.NewsActions {

    private View mainView;
    private List<News> newsList = new ArrayList<>();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private int itemType = 1;


    private NewsSearchPresenter newsSearchPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.search_news_fragment, container, false);
        initViews();
        newsSearchPresenter = new NewsSearchPresenterImp(getActivity());
        newsSearchPresenter.setView(this);
        return mainView;
    }

    @Override
    protected void initViews() {

        progressBar = mainView.findViewById(R.id.progress_bar);
        recyclerView = mainView.findViewById(R.id.search_recycler_view);
        newsAdapter = new NewsAdapter(getActivity(), newsList, itemType);
        recyclerView.setAdapter(newsAdapter);

    }

    @Override
    protected void initPresenter() {


    }


    @Override
    public void viewNewsList(List<News> newsList) {

        this.newsList.clear();
        this.newsList.addAll(newsList);
        newsAdapter.notifyDataSetChanged();

    }

    @Override
    public void hideNewsList() {
        this.newsList.clear();
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void search(String query) {
        if (newsSearchPresenter != null) {
            newsSearchPresenter.findNews(query);
        }

    }

    @Override
    public void onNewsClicked(News news) {

    }
}
