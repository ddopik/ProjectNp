package com.spade.nrc.ui.search.view.channelsSearch;

import com.spade.nrc.ui.shows.model.Channel;
import com.spade.nrc.ui.shows.model.Show;

import java.util.List;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public interface FragmentSearchChannelsView {
    void viewSearchChannels(String key);


    void viewChannelsList(List<Channel> channelListList);
    void hideChannelsList();


    void viewStateMessage(String msg);
    void showProgressBar();

    void hideProgressBar();
}
