package com.spade.nrc.ui.search.presenter.channelsPresenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.search.view.channelsSearch.ChannelsSearchView;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public interface ChannelsSearchPresenter extends BasePresenter<ChannelsSearchView> {
    void findChannels(String key);
}
