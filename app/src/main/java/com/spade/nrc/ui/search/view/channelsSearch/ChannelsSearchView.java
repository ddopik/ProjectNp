package com.spade.nrc.ui.search.view.channelsSearch;

import com.spade.nrc.base.BaseView;
import com.spade.nrc.ui.shows.model.Channel;

import java.util.List;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public interface ChannelsSearchView extends BaseView {

    void viewChannelsList(List<Channel> channelListList);

    void hideChannelsList();
}
