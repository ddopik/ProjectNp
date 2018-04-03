package com.spade.nrc.ui.search.presenter;

import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public interface SearchFragmentPresenter {


     List<Fragment> getViewPagerFragment();
     List<String> getViewPagerFragmentsTitles();
}
