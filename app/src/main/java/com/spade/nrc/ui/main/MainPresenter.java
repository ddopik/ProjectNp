package com.spade.nrc.ui.main;

import com.spade.nrc.base.BasePresenter;

/**
 * Created by Ayman Abouzeid on 4/2/18.
 */

public interface MainPresenter extends BasePresenter<MainView> {
    void logout();

    void login();

    void checkToLogoutOrLogin();
}
