package com.spade.nrc.ui.explore.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.explore.view.ExploreView;

/**
 * Created by Ayman Abouzeid on 1/3/18.
 */

public interface ExplorePresenter extends BasePresenter<ExploreView> {

    void getFeaturedShows();

    void getLiveNowShows();

    void getSlidingBanners();
}
