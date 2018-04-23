package com.spade.nrc.ui.explore.view;

import com.bumptech.glide.request.RequestOptions;
import com.spade.nrc.base.BaseView;
import com.spade.nrc.ui.explore.model.LiveShowsData;
import com.spade.nrc.ui.explore.model.SlideBanner;
import com.spade.nrc.ui.shows.model.Show;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/3/18.
 */

public interface ExploreView extends BaseView {
    void showSliderProgress();

    void hideSliderProgress();

    void showFeaturedShowsProgress();

    void hideFeaturedShowsProgress();

    void showLiveShowsProgress();

    void hideLiveShowsProgress();

    void showSlides(List<SlideBanner> slideBanners);

    void showFeaturedShows(List<Show> showList);

    void showLiveNowShows(List<LiveShowsData> showList);

    void showProfilePic(String url, RequestOptions requestOptions);

    void hideProfilePic();
}
