package com.spade.nrc.ui.search.presenter.showsPresenter;

;import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.search.view.ShowSearch.ShowsSearchView;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public interface ShowSearchPresenter extends BasePresenter<ShowsSearchView> {

   void findShows(String key);
}
