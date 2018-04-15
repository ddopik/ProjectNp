package com.spade.nrc.ui.profile.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.profile.view.UserFavouriteChannelsView;

/**
 * Created by Ayman Abouzeid on 4/13/18.
 */

public interface UserFavouriteChannelsPresenter extends BasePresenter<UserFavouriteChannelsView> {
    void getUSerChannels();
}
