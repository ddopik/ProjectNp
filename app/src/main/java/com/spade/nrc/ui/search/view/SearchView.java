package com.spade.nrc.ui.search.view;

import android.support.v4.app.Fragment;

import com.spade.nrc.base.BaseView;

import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public interface SearchView extends BaseView {
    void addFragment(List<Fragment> fragmentList, List<String> fragmentTitles);
}
