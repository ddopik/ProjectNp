package com.spade.nrc.ui.login.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.androidnetworking.error.ANError;
import com.spade.nrc.R;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.realm.RealmDbHelper;
import com.spade.nrc.realm.RealmDbImpl;
import com.spade.nrc.ui.login.UserModel;
import com.spade.nrc.ui.login.view.LoginView;
import com.spade.nrc.ui.register.RegistrationResponse;
import com.spade.nrc.utils.ErrorUtils;
import com.spade.nrc.utils.LoginProviders;
import com.spade.nrc.utils.PrefUtils;
import com.spade.sociallogin.FacebookLoginCallBack;
import com.spade.sociallogin.FacebookLoginManager;
import com.spade.sociallogin.GoogleLoginCallBack;
import com.spade.sociallogin.GoogleLoginManager;
import com.spade.sociallogin.SocialUser;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public class LoginPresenterImpl implements LoginPresenter, GoogleLoginCallBack, FacebookLoginCallBack {

    public static final String FACEBOOK_TYPE = "Facebook";
    public static final String GOOGLE_TYPE = "Google";

    private LoginView mLoginView;
    private GoogleLoginManager mGoogleLoginManager;
    private FacebookLoginManager mFacebookLoginManager;
    private Context mContext;
    private RealmDbHelper realmDbHelper;


    public LoginPresenterImpl(LoginView loginView, Context context) {
        setView(loginView);
        mContext = context;
        realmDbHelper = new RealmDbImpl();
    }

//    public LoginPresenterImpl(LoginDialogView loginDialogView, Context context) {
//        mContext = context;
//        realmDbHelper = new RealmDbImpl();
//        this.loginDialogView = loginDialogView;
//    }


    @Override
    public void initLoginManagers(FragmentActivity loginActivity) {
        mGoogleLoginManager = new GoogleLoginManager(loginActivity, this);
        mFacebookLoginManager = new FacebookLoginManager(this);
    }

    @Override
    public void loginWithFacebook(Fragment loginFragment) {
        mFacebookLoginManager.loginWithFacebook(loginFragment);

    }

    @Override
    public void loginWithGoogle() {
        mGoogleLoginManager.loginWithGoogle();
    }

    @Override
    public void loginWithGoogle(Fragment fragment) {
        mGoogleLoginManager.loginWithGoogle(fragment);
    }

    @Override
    public void loginAsGuest() {
        PrefUtils.setLoginProvider(mContext, LoginProviders.NONE.getLoginProviderCode());
        mLoginView.navigateToMainScreen();
    }

    @Override
    public void serverLogin(UserModel userModel) {
        mLoginView.showLoading();
        ApiHelper.loginUser(userModel, PrefUtils.getAppLang(mContext), PrefUtils.getNotificationToken(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    if (loginResponse != null) {
                        PrefUtils.setLoginProvider(mContext, LoginProviders.SERVER_LOGIN.getLoginProviderCode());
                        completeLogin(loginResponse);
                    }
                }, throwable -> {
                    mLoginView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mLoginView.showMessage(ErrorUtils.getErrors(anError));
                    }
                });
    }

    @Override
    public void googleLogout() {
        mGoogleLoginManager.logout();
    }

    @Override
    public void facebookLogout() {
        mFacebookLoginManager.logout();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mGoogleLoginManager.onActivityResult(requestCode, data);
        mFacebookLoginManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void disconnectGoogleApiClient() {
        mGoogleLoginManager.disconnectGoogleApi();
    }

    @Override
    public void setView(LoginView view) {
        mLoginView = view;
    }


    @Override
    public void onGoogleLoginSuccess(SocialUser socialUser) {
        PrefUtils.setUserID(mContext, socialUser.getUserId());
        socialLoginUser(socialUser, GOOGLE_TYPE);
    }

    private void socialLoginUser(SocialUser socialUser, String type) {
        ApiHelper.socialLoginUSer(socialUser, type, PrefUtils.getAppLang(mContext), PrefUtils.getNotificationToken(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registrationResponse -> {
                    if (registrationResponse != null) {
                        realmDbHelper.saveUser(socialUser);
                        PrefUtils.setUserToken(mContext, registrationResponse.getRegistrationResponseData().getToken());
                        if (type.equals(GOOGLE_TYPE))
                            PrefUtils.setLoginProvider(mContext, LoginProviders.GOOGLE.getLoginProviderCode());
                        else
                            PrefUtils.setLoginProvider(mContext, LoginProviders.FACEBOOK.getLoginProviderCode());
                        mLoginView.finish();
                        mLoginView.navigate();
                    }
                }, throwable -> {
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        mLoginView.showMessage(ErrorUtils.getErrors(anError));
                    }
                });
    }

    private void completeLogin(RegistrationResponse response) {
        PrefUtils.setUserID(mContext, response.getRegistrationResponseData().getUserModel().getUserId());
        PrefUtils.setUserToken(mContext, response.getRegistrationResponseData().getToken());
        realmDbHelper.saveUser(response.getRegistrationResponseData().getUserModel(),
                response.getRegistrationResponseData().getToken());
        mLoginView.hideLoading();
        mLoginView.finish();
        mLoginView.navigate();

//        UserModel userDataModel = response.getRegistrationResponseData().getUserModel();
//        realmDbHelper.updateUserData(userDataModel.getFirstName(), userDataModel.getLastName(), userDataModel.getUserPhone(),
//                userDataModel.getUserEmail(), userDataModel.getUserProfileImage(), userDataModel.getUserId());
//        realmDbHelper.updateCartItemsWithLoggedInUser(userDataModel.getUserId()).subscribe(isSuccess -> {
//            if (isSuccess) {
//                changeLanguage();
//                PrefUtils.setLoginProvider(mContext, LoginProviders.SERVER_LOGIN.getLoginProviderCode());
//                PrefUtils.setUserID(mContext, userDataModel.getUserId());
//                PrefUtils.setUserToken(mContext, response.getRegistrationResponseData().getToken());
//
//                if (mLoginView != null) {
//                    mLoginView.hideLoading();
//                    mLoginView.finish();
//                    mLoginView.navigate();
//                } else {
//                    loginDialogView.loginSuccess();
//                }
//            }
//        });

    }

    @Override
    public void onGoogleLoginFail() {
        if (mLoginView != null)
            mLoginView.showMessage(R.string.something_wrong);
    }

    @Override
    public void onFacebookLoginSuccess(SocialUser socialUser) {
        PrefUtils.setUserID(mContext, socialUser.getUserId());
        socialLoginUser(socialUser, FACEBOOK_TYPE);
    }

//    @SuppressWarnings("deprecation")
//    public void changeLanguage() {
//        Locale locale;
//        if (PrefUtils.isLanguageSelected(mContext)) {
//            if (PrefUtils.getAppLang(mContext).equals(PrefUtils.ARABIC_LANG)) {
//                locale = new Locale(MorePresenterImpl.AR_LANG);
//            } else {
//                locale = new Locale(MorePresenterImpl.EN_LANG);
//            }
//            Configuration conf = new Configuration();
//            conf.locale = locale;
//            mContext.getResources().updateConfiguration(conf, mContext.getResources().getDisplayMetrics());
//        } else {
//            String deviceLang = Locale.getDefault().getLanguage();
//            if (!deviceLang.equals(PrefUtils.ARABIC_LANG)) {
//                PrefUtils.setAppLang(mContext, PrefUtils.ENGLISH_LANG);
//            } else {
//                PrefUtils.setAppLang(mContext, PrefUtils.ARABIC_LANG);
//            }
//        }
//    }

    @Override
    public void onFacebookLoginCancel() {

    }

    @Override
    public void onFacebookLoginFail(Exception e) {
        if (mLoginView != null)
            mLoginView.showMessage(R.string.something_wrong);
    }


}
