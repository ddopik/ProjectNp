package com.spade.nrc.realm;


import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.shows.model.ChannelRealm;
import com.spade.nrc.ui.shows.model.ShowRealm;

import io.realm.annotations.RealmModule;

/**
 * Created by Ayman Abouzeid on 6/13/17.
 */

@RealmModule(classes = {User.class, ChannelRealm.class, ShowRealm.class})
public class RealmModules {
}
