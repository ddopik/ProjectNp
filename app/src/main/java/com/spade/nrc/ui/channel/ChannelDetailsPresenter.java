package com.spade.nrc.ui.channel;

import com.spade.nrc.base.BasePresenter;

/**
 * Created by Ayman Abouzeid on 1/14/18.
 */

public interface ChannelDetailsPresenter extends BasePresenter<ChannelsDetailsView> {
    void setUpViewPager(int channelID);
}
