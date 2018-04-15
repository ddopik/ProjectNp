package com.spade.nrc.ui.search.presenter.presentersPresenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.search.view.presentersSearch.PresentersSearchView;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public interface PresentersSearchPresenter extends BasePresenter<PresentersSearchView> {

    void findPresenter(String key);
}
