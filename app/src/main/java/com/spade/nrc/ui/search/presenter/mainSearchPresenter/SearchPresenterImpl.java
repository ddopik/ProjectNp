package com.spade.nrc.ui.search.presenter.mainSearchPresenter;

import android.support.v4.app.Fragment;

import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.ui.search.view.channelsSearch.SearchChannelsFragment;
import com.spade.nrc.ui.search.view.presentersSearch.FragmentSearchPresenters;
import com.spade.nrc.ui.search.view.ShowSearch.FragmentSearchShow;
import com.spade.nrc.ui.search.view.SearchFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class SearchFragmentPresenterImpl implements SearchFragmentPresenter {


    private List<Fragment> fragmentList;
    private List<String> fragmentTitle;
    private SearchFragmentView searchFragmentView;
    private FragmentSearchShow fragmentSearchShow;
    private SearchChannelsFragment searchChannelsFragment;
    private FragmentSearchPresenters fragmentSearchPresenters;


    public SearchFragmentPresenterImpl(SearchFragmentView searchFragmentView) {
        this.searchFragmentView = searchFragmentView;
        fragmentSearchShow = new FragmentSearchShow();
        searchChannelsFragment = new SearchChannelsFragment();
        fragmentSearchPresenters = new FragmentSearchPresenters();
    }

    @Override

    public List<Fragment> getViewPagerFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentSearchShow);
        fragmentList.add(searchChannelsFragment);
        fragmentList.add(fragmentSearchPresenters);
        //fragmentList.add(new FragmentSearchNews());
        return fragmentList;
    }


    @Override
    public List<String> getViewPagerFragmentsTitles() {
        fragmentTitle = new ArrayList<>();
        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.shows));
        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.channels));
//        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.news));
        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.presenters));

        return fragmentTitle;
    }

    @Override
    public void notifyFragment(String key) {

        fragmentSearchShow.ViewSearchShow(key);
        searchChannelsFragment.viewSearchChannels(key);
        fragmentSearchPresenters.ViewSearchPresenters(key);

    }


}
