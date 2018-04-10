package com.spade.nrc.realm;

import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.login.UserModel;
import com.spade.nrc.ui.shows.model.Channel;
import com.spade.nrc.ui.shows.model.ChannelRealm;
import com.spade.nrc.ui.shows.model.ShowRealm;
import com.spade.sociallogin.SocialUser;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

public interface RealmDbHelper {
    void saveUser(SocialUser socialUser);

    void saveUser(UserModel userModel, String userToken);

    void deleteUser(String userId);

    User getUser(String userId);

    void saveChannels(List<Channel> channelList);

    void updateChannelData(ChannelRealm channelRealm);

    void updateShowData(ShowRealm showRealm);

    boolean isChannelLiked(int channelID);

    boolean isShowLiked(int showID);

}
