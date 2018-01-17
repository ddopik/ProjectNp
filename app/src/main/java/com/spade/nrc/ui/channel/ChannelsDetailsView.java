package com.spade.nrc.ui.channel;

import android.support.v4.app.Fragment;

import com.spade.nrc.base.BaseView;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 1/14/18.
 */

public interface ChannelsDetailsView extends BaseView {
    void addFragment(List<Fragment> fragmentList, List<String> fragmentTitles);
}

