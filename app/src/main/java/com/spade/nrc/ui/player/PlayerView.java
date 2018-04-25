package com.spade.nrc.ui.player;

import com.spade.nrc.base.BaseView;
import com.spade.nrc.ui.shows.model.Show;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/3/18.
 */

public interface PlayerView extends BaseView {
//    void showLiveNowShows(List<Show> showList);
//
//    void updateUI(Show show);

    void showCurrentShow(Show currentShow);

    void showNextShow(Show nextShow);

    void updateAddToFavouriteNextBtn(int showId, boolean isFav);
}
