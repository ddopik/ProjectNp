package com.spade.nrc.ui.register;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.realm.RealmDbHelper;
import com.spade.nrc.realm.RealmDbImpl;
import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.login.UserModel;
import com.spade.nrc.utils.ErrorUtils;
import com.spade.nrc.utils.LoginProviders;
import com.spade.nrc.utils.PrefUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public class RegisterPresenterImpl implements RegisterPresenter {

    private Context mContext;
    private RealmDbHelper realmDbHelper;
    private RegisterView registerView;
//    private UserModel userModel;

    public RegisterPresenterImpl(Context mContext) {
        this.mContext = mContext;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(RegisterView view) {
        this.registerView = view;
    }


    @Override
    public User getUser(String userId) {
        return realmDbHelper.getUser(userId);
    }

    @Override
    public void register(UserModel userModel) {
//        this.userModel = userModel;
        registerView.showLoading();
        ApiHelper.registerUser(userModel, PrefUtils.getNotificationToken(mContext), PrefUtils.getAppLang(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registrationResponse -> {
                    if (registrationResponse != null && registrationResponse.isSuccess()) {
                        realmDbHelper.saveUser(registrationResponse.getRegistrationResponseData().getUserModel(),
                                registrationResponse.getRegistrationResponseData().getToken());
                        PrefUtils.setLoginProvider(mContext, LoginProviders.SERVER_LOGIN.getLoginProviderCode());
                        PrefUtils.setUserID(mContext, userModel.getUserId());
                        PrefUtils.setUserToken(mContext, registrationResponse.getRegistrationResponseData().getToken());
                        registerView.hideLoading();
                        registerView.finish();
                        registerView.navigate();
                    }
                }, throwable -> {
                    registerView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        registerView.showMessage(ErrorUtils.getErrors(anError));
                    }
                });

//        new CreateJsonRequestObject().execute();
    }

//    private void registerUser(JSONObject requestJson) {
//        registerView.showLoading();
//        ApiHelper.registerUser(requestJson)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(registrationResponse -> {
//                    if (registrationResponse != null && registrationResponse.isSuccess()) {
//                        realmDbHelper.saveUser(registrationResponse.getRegistrationResponseData().getUserModel(),
//                                registrationResponse.getRegistrationResponseData().getToken());
//                        UserModel userModel = registrationResponse.getRegistrationResponseData().getUserModel();
//                        realmDbHelper.updateUserData(userModel.getFirstName(), userModel.getLastName(), userModel.getUserPhone(),
//                                userModel.getUserEmail(), userModel.getUserProfileImage(), userModel.getUserId());
//                        realmDbHelper.updateCartItemsWithLoggedInUser(userModel.getUserId()).subscribe(isSuccess -> {
//                            if (isSuccess) {
//                                PrefUtils.setLoginProvider(mContext, LoginProviders.SERVER_LOGIN.getLoginProviderCode());
//                                PrefUtils.setUserID(mContext, userModel.getUserId());
//                                PrefUtils.setUserToken(mContext, registrationResponse.getRegistrationResponseData().getToken());
//
//                                registerView.hideLoading();
//                                registerView.finish();
//                                registerView.navigate();
//                            }
//                        });
//                    }
//                }, throwable -> {
//                    registerView.hideLoading();
//                    if (throwable != null) {
//                        ANError anError = (ANError) throwable;
//                        registerView.onError(ErrorUtils.getErrors(anError));
//                    }
//                });
//    }


//    private class CreateJsonRequestObject extends AsyncTask<Void, Void, JSONObject> {
//        @Override
//        protected JSONObject doInBackground(Void... params) {
//            JSONObject requestJsonObject = null;
//            try {
//                requestJsonObject = new JSONObject();
//                requestJsonObject.put("first_name", userModel.getFirstName());
//                requestJsonObject.put("last_name", userModel.getLastName());
//                requestJsonObject.put("email", userModel.getUserEmail());
//                requestJsonObject.put("phone", userModel.getUserPhone());
//                requestJsonObject.put("address", userModel.getUserProfileImage());
//                requestJsonObject.put("password", userModel.getPassword());
//                requestJsonObject.put("notification_token", PrefUtils.getNotificationToken(mContext));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return requestJsonObject;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//            super.onPostExecute(jsonObject);
//            registerUser(jsonObject);
//        }
//    }
}
