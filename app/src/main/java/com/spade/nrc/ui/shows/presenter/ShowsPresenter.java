package com.spade.nrc.ui.shows.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.presenters.view.PresentersView;
import com.spade.nrc.ui.shows.view.ShowsView;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public interface ShowsPresenter extends BasePresenter<ShowsView> {
    void getShows(String appLang, int channelID);

    void getShowsByDay(String appLang, String day, int channelID);

}
