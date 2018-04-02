package com.spade.nrc.realm;

import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.login.UserModel;
import com.spade.sociallogin.SocialUser;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

public interface RealmDbHelper {
    void saveUser(SocialUser socialUser);

    void saveUser(UserModel userModel, String userToken);

    void updateUserData(String firstName, String lastName, String phoneNumber, String emailAddress, String address, String userId);

    void deleteUser(String userId);

    User getUser(String userId);

}
