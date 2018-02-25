package com.spade.nrc.ui.channel.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.channel.view.AboutChannelView;

/**
 * Created by Ayman Abouzeid on 2/5/18.
 */

public interface AboutChannelPresenter extends BasePresenter<AboutChannelView> {
    void getChannelInfo(int channelID);
}
