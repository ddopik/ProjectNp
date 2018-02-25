package com.spade.nrc.ui.channel.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.channel.view.LiveStreamingView;

/**
 * Created by Ayman Abouzeid on 1/31/18.
 */

public interface LiveStreamingPresenter extends BasePresenter<LiveStreamingView> {
    void getLiveStreamingShows(String channelID);
}
