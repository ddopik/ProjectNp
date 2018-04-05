package com.spade.nrc.ui.search.presenter;

import android.support.v4.app.Fragment;

import com.spade.nrc.R;
import com.spade.nrc.application.NRCApplication;
import com.spade.nrc.ui.search.view.FragmentSearchChannels;
import com.spade.nrc.ui.search.view.FragmentSearchChannelsView;
import com.spade.nrc.ui.search.view.FragmentSearchPresenters;
import com.spade.nrc.ui.search.view.FragmentSearchPresentersView;
import com.spade.nrc.ui.search.view.FragmentSearchShow;
import com.spade.nrc.ui.search.view.FragmentSearchShowView;
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
    private FragmentSearchChannels fragmentSearchChannels;
    private FragmentSearchPresenters fragmentSearchPresenters;



    public SearchFragmentPresenterImpl(SearchFragmentView searchFragmentView) {
        this.searchFragmentView = searchFragmentView;
        fragmentSearchShow = new FragmentSearchShow();
        fragmentSearchChannels = new FragmentSearchChannels();
        fragmentSearchPresenters = new FragmentSearchPresenters();
    }

    @Override

    public List<Fragment> getViewPagerFragment() {


        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(fragmentSearchShow);
        fragmentList.add(fragmentSearchChannels);
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

        ((FragmentSearchShowView) fragmentSearchShow).ViewSearchShow(key);
        ((FragmentSearchChannelsView) fragmentSearchChannels).ViewChannelsShow(key);
        ((FragmentSearchPresentersView) fragmentSearchPresenters).ViewSearchPresenters(key);

    }



}
