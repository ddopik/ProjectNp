package com.spade.nrc.ui.channel.view;

import com.spade.nrc.base.BaseView;
import com.spade.nrc.ui.shows.model.Show;

/**
 * Created by Ayman Abouzeid on 1/31/18.
 */

public interface LiveStreamingView extends BaseView {
    void showCurrentShow(Show currentShow);

    void showNextShow(Show nextShow);

    void updateFavBtn();
}
