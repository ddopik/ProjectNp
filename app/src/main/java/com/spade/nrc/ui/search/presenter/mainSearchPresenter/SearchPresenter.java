package com.spade.nrc.ui.search.presenter.mainSearchPresenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.search.view.SearchView;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public interface SearchPresenter extends BasePresenter<SearchView> {

//    List<Fragment> getViewPagerFragment();

//    List<String> getViewPagerFragmentsTitles();

    void notifyFragment(String key);

    void setUpViewPager();

}
