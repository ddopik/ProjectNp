package com.spade.nrc.ui.profile.view;

import com.spade.nrc.base.BaseView;
import com.spade.nrc.ui.shows.model.Channel;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 4/13/18.
 */

public interface UserFavouriteChannelsView extends BaseView {
    void showChannels(List<Channel> channelList);
}
