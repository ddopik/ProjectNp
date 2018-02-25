package com.spade.nrc.ui.channel.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.channel.view.ChannelsView;

/**
 * Created by Ayman Abouzeid on 1/31/18.
 */

public interface ChannelsPresenter extends BasePresenter<ChannelsView> {
    void getChannels();
}
