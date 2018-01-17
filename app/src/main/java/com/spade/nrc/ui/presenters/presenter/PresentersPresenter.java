package com.spade.nrc.ui.presenters.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.presenters.view.PresentersView;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public interface PresentersPresenter extends BasePresenter<PresentersView> {
    void getPresenters(String appLang, int channelID);
}
