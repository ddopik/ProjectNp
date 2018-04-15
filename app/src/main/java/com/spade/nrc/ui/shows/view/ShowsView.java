package com.spade.nrc.ui.shows.view;

import com.spade.nrc.base.BaseView;
import com.spade.nrc.ui.ads.AdModel;
import com.spade.nrc.ui.shows.model.Show;
import com.spade.nrc.ui.shows.model.ShowsData;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public interface ShowsView extends BaseView {
    void displayShows(List<Show> showList);

    void displayShows(ShowsData showsData);

    void displayAds(List<AdModel> adModels);

    void showProgress();

    void hideProgress();

    void updateAddToFavouriteBtn();
}
