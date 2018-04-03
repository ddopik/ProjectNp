package com.spade.nrc.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.spade.nrc.realm.RealmDbHelper;
import com.spade.nrc.realm.RealmDbImpl;
import com.spade.nrc.ui.login.view.LoginActivity;
import com.spade.nrc.ui.splash.SplashActivity;
import com.spade.nrc.utils.LoginProviders;
import com.spade.nrc.utils.PrefUtils;

/**
 * Created by Ayman Abouzeid on 4/2/18.
 */

public class MainPresenterImpl implements MainPresenter {

    private Context context;
    private RealmDbHelper realmDbHelper;

    public MainPresenterImpl(Context context) {
        this.context = context;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(MainView view) {

    }

    @Override
    public void logout() {
        realmDbHelper.deleteUser(PrefUtils.getUserId(context));
        PrefUtils.setNotificationToken(context, "");
        PrefUtils.setUserID(context, "");
        PrefUtils.setIsLoggedIn(context, false);
        PrefUtils.setLoginProvider(context, LoginProviders.NONE.getLoginProviderCode());
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    @Override
    public void login() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    @Override
    public void checkToLogoutOrLogin() {
        if (PrefUtils.getLoginProvider(context) == LoginProviders.NONE.getLoginProviderCode()) {
            login();
        } else {
            logout();
        }
    }
}
