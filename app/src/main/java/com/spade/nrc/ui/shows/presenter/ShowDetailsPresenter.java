package com.spade.nrc.ui.shows.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.shows.view.ShowDetailsView;

/**
 * Created by Ayman Abouzeid on 1/25/18.
 */

public interface ShowDetailsPresenter extends BasePresenter<ShowDetailsView> {
    void getShowDetails(String showID);
}
