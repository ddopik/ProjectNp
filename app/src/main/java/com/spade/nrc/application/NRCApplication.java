package com.spade.nrc.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.onesignal.OneSignal;
import com.spade.nrc.realm.RealmConfig;
import com.spade.nrc.realm.RealmDbMigration;
import com.spade.nrc.realm.RealmModules;
import com.spade.nrc.utils.PrefUtils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Ayman Abouzeid on 3/1/18.
 */

public class NRCApplication extends Application {

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;
    public static NRCApplication nrcApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        nrcApplication = this;
        initGoogleAnalytics();
        initOneSignal();
        initRealm();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initGoogleAnalytics() {
        sAnalytics = GoogleAnalytics.getInstance(this);
    }

    private void initOneSignal() {
        OneSignal.startInit(this).init();
        OneSignal.idsAvailable((userId, registrationId) -> PrefUtils.setNotificationToken(this, userId));
    }

    synchronized public static Tracker getDefaultTracker() {
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker("UA-114968638-1");
        }
        return sTracker;
    }

    private void initRealm() {
        Realm.init(this);
        setRealmDefaultConfiguration();
    }

    private void setRealmDefaultConfiguration() {
//        if (PrefUtils.isFirstLaunch(this)) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().
                name(RealmConfig.REALM_FILE).
                schemaVersion(RealmConfig.REALM_VERSION).migration(new RealmDbMigration()).
                modules(new RealmModules()).build();
        Realm.setDefaultConfiguration(realmConfiguration);
//        }
    }
}
