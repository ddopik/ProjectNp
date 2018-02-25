package com.spade.nrc.ui.channel.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.channel.view.ScheduleView;

/**
 * Created by Ayman Abouzeid on 1/14/18.
 */

public interface SchedulePresenter extends BasePresenter<ScheduleView> {
    void setUpViewPager(int channelID);
}
