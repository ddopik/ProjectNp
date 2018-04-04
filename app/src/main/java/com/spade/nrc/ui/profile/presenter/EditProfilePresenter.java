package com.spade.nrc.ui.profile.presenter;


import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.base.BasePresenter;
import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.login.UserModel;
import com.spade.nrc.ui.profile.view.EditProfileView;

import java.io.File;

/**
 * Created by Ayman Abouzeid on 9/5/17.
 */

public interface EditProfilePresenter extends BasePresenter<EditProfileView> {

    void editProfile(UserModel userModel, File file);

    User getUser(String userId);

    void pickImageFromCamera(BaseFragment baseFragment);

    void pickImageFromGallery(BaseFragment baseFragment);
}
