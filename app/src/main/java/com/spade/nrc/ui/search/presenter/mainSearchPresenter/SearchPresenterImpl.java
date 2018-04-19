package com.spade.nrc.ui.search.presenter.mainSearchPresenter;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.spade.nrc.R;
import com.spade.nrc.ui.search.view.NewsSearch.NewsSearchFragment;
import com.spade.nrc.ui.search.view.SearchView;
import com.spade.nrc.ui.search.view.ShowSearch.ShowsSearchFragment;
import com.spade.nrc.ui.search.view.channelsSearch.ChannelsSearchFragment;
import com.spade.nrc.ui.search.view.presentersSearch.PresentersSearchFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class SearchPresenterImpl implements SearchPresenter {


    //    private List<Fragment> fragmentList;
//    private List<String> fragmentTitle;
    private SearchView searchView;
    private ShowsSearchFragment showsSearchFragment;
    private ChannelsSearchFragment channelsSearchFragment;
    private PresentersSearchFragment presentersSearchFragment;
    private NewsSearchFragment newsSearchFragment;
    private Context context;

    public SearchPresenterImpl(Context context) {
        this.context = context;
    }

//    @Override
//
//    public List<Fragment> getViewPagerFragment() {
//        fragmentList = new ArrayList<>();
//        fragmentList.add(fragmentSearchShow);
//        fragmentList.add(searchChannelsFragment);
//        fragmentList.add(fragmentSearchPresenters);
//        //fragmentList.add(new FragmentSearchNews());
//        return fragmentList;
//    }
//
//
//    @Override
//    public List<String> getViewPagerFragmentsTitles() {
//        fragmentTitle = new ArrayList<>();
//        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.shows));
//        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.channels));
////        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.news));
//        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.presenters));
//
//        return fragmentTitle;
//    }

    @Override
    public void notifyFragment(String key) {
        showsSearchFragment.search(key);
        channelsSearchFragment.search(key);
        presentersSearchFragment.search(key);
        newsSearchFragment.search(key);
    }

    @Override
    public void setUpViewPager() {
        showsSearchFragment = new ShowsSearchFragment();
        channelsSearchFragment = new ChannelsSearchFragment();
        presentersSearchFragment = new PresentersSearchFragment();
        newsSearchFragment = new NewsSearchFragment();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(showsSearchFragment);
        fragmentList.add(channelsSearchFragment);
        fragmentList.add(newsSearchFragment);
        fragmentList.add(presentersSearchFragment);

        List<String> fragmentTitle = new ArrayList<>();
        fragmentTitle.add(context.getString(R.string.shows));
        fragmentTitle.add(context.getString(R.string.channels));
        fragmentTitle.add(context.getString(R.string.news));
        fragmentTitle.add(context.getString(R.string.presenters));
        searchView.addFragment(fragmentList, fragmentTitle);
    }


    @Override
    public void setView(SearchView view) {
        searchView = view;
    }
}
