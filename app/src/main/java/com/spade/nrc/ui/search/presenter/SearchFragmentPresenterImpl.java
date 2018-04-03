package com.spade.nrc.ui.search.presenter;

import android.support.v4.app.Fragment;

import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.ui.search.view.FragmentSearchChannels;
import com.spade.nrc.ui.search.view.FragmentSearchNews;
import com.spade.nrc.ui.search.view.FragmentSearchPresenters;
import com.spade.nrc.ui.search.view.FragmetnSreachShow;
import com.spade.nrc.ui.search.view.SearchFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class SearchFragmentPresenterImpl implements SearchFragmentPresenter {

    private SearchFragmentView searchFragmentView;
    private List<Fragment> fragmentList;
    private List<String> fragmentTitle;

    @Override
    public List<Fragment> getViewPagerFragment() {
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new FragmetnSreachShow());
        fragmentList.add(new FragmentSearchChannels());
        fragmentList.add(new FragmentSearchNews());
        fragmentList.add(new FragmentSearchPresenters());
        return fragmentList;
    }


    @Override
    public List<String> getViewPagerFragmentsTitles() {
        fragmentTitle=new ArrayList<>();
        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.shows));
        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.channels));
        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.news));
        fragmentTitle.add(NRCApplication.nrcApplication.getString(R.string.presenters));

        return fragmentTitle;
    }

    public SearchFragmentPresenterImpl(SearchFragmentView searchFragmentView) {
        this.searchFragmentView = searchFragmentView;
    }


}
