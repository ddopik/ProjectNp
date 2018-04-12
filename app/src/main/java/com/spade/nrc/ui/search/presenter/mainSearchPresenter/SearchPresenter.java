package com.spade.nrc.ui.search.presenter.mainSearchPresenter;

import android.support.v4.app.Fragment;

import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.ui.shows.model.Channel;
import com.spade.nrc.ui.shows.model.Show;

import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public interface SearchFragmentPresenter {

    List<Fragment> getViewPagerFragment();

    List<String> getViewPagerFragmentsTitles();

    void notifyFragment(String key);

    void setUpViewPager();

}