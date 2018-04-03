package com.spade.nrc.realm;

import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.login.UserModel;
import com.spade.sociallogin.SocialUser;

import io.realm.Realm;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

public class RealmDbImpl implements RealmDbHelper {
    public RealmDbImpl() {
    }

    @Override
    public void saveUser(SocialUser socialUser) {
        Realm realmInstance = Realm.getDefaultInstance();
        realmInstance.beginTransaction();
        User user = new User();
        user.setUserEmail(socialUser.getEmailAddress());
        user.setFirstName(socialUser.getName());
        user.setUserPhoto(socialUser.getUserPhoto());
        user.setUserId(socialUser.getUserId());
        realmInstance.copyToRealmOrUpdate(user);
        realmInstance.commitTransaction();
        realmInstance.close();
    }

    @Override
    public void saveUser(UserModel userModel, String userToken) {
        Realm realmInstance = Realm.getDefaultInstance();
        realmInstance.beginTransaction();
        User user = new User();
        user.setUserEmail(userModel.getUserEmail());
        user.setFirstName(userModel.getFirstName());
        user.setUserId(userModel.getUserId());
        user.setLastName(userModel.getLastName());
        user.setUserPhone(userModel.getUserPhone());
        user.setUserToken(userToken);
        user.setUserPhoto(userModel.getUserProfileImage());
        realmInstance.copyToRealmOrUpdate(user);
        realmInstance.commitTransaction();
        realmInstance.close();
    }

    @Override
    public void updateUserData(String firstName, String lastName, String phoneNumber,
                               String emailAddress, String address, String userId) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        User user = new User();
        user.setUserEmail(emailAddress);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserAddress(address);
        user.setUserPhone(phoneNumber);
        user.setUserId(userId);
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void deleteUser(String userId) {
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("userId", userId).findFirst();
        realm.beginTransaction();
        user.deleteFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public User getUser(String userId) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(User.class).equalTo("userId", userId).findFirst();
    }

}
