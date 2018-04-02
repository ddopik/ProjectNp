package com.spade.nrc.ui.register;


import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.login.UserModel;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public interface RegisterPresenter extends BasePresenter<RegisterView> {
    User getUser(String userId);

    void register(UserModel userModel);
}
