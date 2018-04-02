package com.spade.nrc.ui.login.view;


import com.spade.nrc.base.BaseView;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public interface LoginView extends BaseView {
    void showLoading();

    void hideLoading();

    void finish();

    void navigate();

    void navigateToMainScreen();
}
