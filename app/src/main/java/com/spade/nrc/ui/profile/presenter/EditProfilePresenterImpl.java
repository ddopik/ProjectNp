package com.spade.nrc.ui.profile.presenter;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.network.ApiHelper;
import com.spade.nrc.realm.RealmDbHelper;
import com.spade.nrc.realm.RealmDbImpl;
import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.login.UserModel;
import com.spade.nrc.ui.profile.view.EditProfileView;
import com.spade.nrc.utils.ErrorUtils;
import com.spade.nrc.utils.PrefUtils;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by Ayman Abouzeid on 9/5/17.
 */

public class EditProfilePresenterImpl implements EditProfilePresenter {

    private Context mContext;
    private RealmDbHelper realmDbHelper;
    private EditProfileView editProfileView;

    public EditProfilePresenterImpl(Context mContext) {
        this.mContext = mContext;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(EditProfileView view) {
        editProfileView = view;
    }

    @Override
    public void editProfile(UserModel userModel, File file) {
        editProfileView.showLoading();
        ApiHelper.editProfile(userModel, PrefUtils.getUserToken(mContext), file, PrefUtils.getNotificationToken(mContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(registrationResponse -> {
                    realmDbHelper.saveUser(registrationResponse.getRegistrationResponseData().getUserModel(), PrefUtils.getUserToken(mContext));
                    editProfileView.hideLoading();
                    editProfileView.finish();
                }, throwable -> {
                    editProfileView.hideLoading();
                    if (throwable != null) {
                        ANError anError = (ANError) throwable;
                        editProfileView.showMessage(ErrorUtils.getErrors(anError));
                    }
                });
    }

    @Override
    public User getUser(String userId) {
        return realmDbHelper.getUser(userId);
    }

    @Override
    public void pickImageFromCamera(BaseFragment baseFragment) {
        EasyImage.openCamera(baseFragment, 0);
    }

    @Override
    public void pickImageFromGallery(BaseFragment baseFragment) {
        EasyImage.openGallery(baseFragment, 0);
    }
}
