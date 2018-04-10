package com.spade.nrc.realm;

import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.login.UserModel;
import com.spade.nrc.ui.shows.model.Channel;
import com.spade.nrc.ui.shows.model.ChannelRealm;
import com.spade.nrc.ui.shows.model.ShowRealm;
import com.spade.sociallogin.SocialUser;

import java.util.List;

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

    @Override
    public void saveChannels(List<Channel> channelList) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (Channel channel : channelList) {
            ChannelRealm channelRealm = new ChannelRealm();
            channelRealm.setId(channel.getId());
            channelRealm.setLiked(channel.isLiked());
            realm.copyToRealmOrUpdate(channelRealm);
        }
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void updateChannelData(ChannelRealm channelRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ChannelRealm channel = new ChannelRealm();
        channel.setId(channelRealm.getId());
        channel.setLiked(channelRealm.isLiked());
        realm.copyToRealmOrUpdate(channel);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public void updateShowData(ShowRealm showRealm) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ShowRealm show = new ShowRealm();
        show.setId(showRealm.getId());
        show.setLiked(showRealm.isLiked());
        realm.copyToRealmOrUpdate(show);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public boolean isChannelLiked(int channelID) {
        Realm realm = Realm.getDefaultInstance();
        ChannelRealm channelRealm = realm.where(ChannelRealm.class).equalTo("id", channelID).findFirst();
        return channelRealm != null && channelRealm.isLiked();
    }

    @Override
    public boolean isShowLiked(int showID) {
        Realm realm = Realm.getDefaultInstance();
        ShowRealm showRealm = realm.where(ShowRealm.class).equalTo("id", showID).findFirst();
        return showRealm != null && showRealm.isLiked();
    }

}
