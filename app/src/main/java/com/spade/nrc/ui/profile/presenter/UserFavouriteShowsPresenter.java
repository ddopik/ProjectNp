package com.spade.nrc.ui.profile.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.profile.view.UserFavouriteChannelsView;
import com.spade.nrc.ui.profile.view.UserFavouriteShowsView;

/**
 * Created by Ayman Abouzeid on 4/13/18.
 */

public interface UserFavouriteShowsPresenter extends BasePresenter<UserFavouriteShowsView> {
    void getUSerShows();
}
