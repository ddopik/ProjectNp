package com.spade.nrc.ui.player;

import com.spade.nrc.base.BasePresenter;

/**
 * Created by Ayman Abouzeid on 1/3/18.
 */

public interface PlayerPresenter extends BasePresenter<PlayerView> {
//    void getLiveNowShows();

    void getLiveStreamingShows(String channelID);
//    void getShowByChannelID(int channelID, List<Show> showList);
}
