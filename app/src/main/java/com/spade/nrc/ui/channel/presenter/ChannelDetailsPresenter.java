package com.spade.nrc.ui.channel.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.channel.view.ChannelsDetailsView;

/**
 * Created by Ayman Abouzeid on 1/14/18.
 */

public interface ChannelDetailsPresenter extends BasePresenter<ChannelsDetailsView> {
    void setUpViewPager(int channelID);

    void addChannelToFav(int channelID);
}
