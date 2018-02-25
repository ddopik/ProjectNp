package com.spade.nrc.ui.presenters.presenter;

import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.presenters.view.PresenterDetailsView;

/**
 * Created by Ayman Abouzeid on 1/25/18.
 */

public interface PresenterDetailsPresenter extends BasePresenter<PresenterDetailsView> {
    void getPresenterDetails(String presenterID, String channelID);
}
