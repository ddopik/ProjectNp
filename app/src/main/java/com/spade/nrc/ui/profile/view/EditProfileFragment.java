package com.spade.nrc.ui.profile.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.dialog.PickImageDialogFragment;
import com.spade.nrc.ui.login.User;
import com.spade.nrc.ui.login.UserModel;
import com.spade.nrc.ui.profile.presenter.EditProfilePresenter;
import com.spade.nrc.ui.profile.presenter.EditProfilePresenterImpl;
import com.spade.nrc.utils.GlideApp;
import com.spade.nrc.utils.PrefUtils;
import com.vansuita.gaussianblur.GaussianBlur;

import java.io.File;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;


/**
 * Created by Ayman Abouzeid on 9/5/17.
 */

public class EditProfileFragment extends BaseFragment implements EditProfileView, PickImageDialogFragment.PickImageActions {

    private EditProfilePresenter editProfilePresenter;
    private View mEditProfileView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumberEditText;
    private String firstNameString, lastNameString, phoneNumberString;
    private ProgressDialog progressDialog;
    private File imageFile;
    private ImageView profilePictureImageView, profilePictureBackgroundImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mEditProfileView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        initViews();
        return mEditProfileView;
    }

    @Override
    protected void initPresenter() {
        editProfilePresenter = new EditProfilePresenterImpl(getContext());
        editProfilePresenter.setView(this);
    }

    @Override
    protected void initViews() {
        EditText emailAddressEditText = mEditProfileView.findViewById(R.id.email_address_edit_text);
        firstNameEditText = mEditProfileView.findViewById(R.id.first_name_edit_text);
        lastNameEditText = mEditProfileView.findViewById(R.id.last_name_edit_text);
        phoneNumberEditText = mEditProfileView.findViewById(R.id.phone_number_edit_text);
        profilePictureImageView = mEditProfileView.findViewById(R.id.profile_image);
        profilePictureBackgroundImageView = mEditProfileView.findViewById(R.id.profile_image_background);

        TextView saveBtn = (Button) mEditProfileView.findViewById(R.id.save_btn);
        TextView choosePic = mEditProfileView.findViewById(R.id.change_picture_text_view);
        emailAddressEditText.setVisibility(View.GONE);
        saveBtn.setOnClickListener(v -> {
            if (checkIfDataIsValid())
                proceed();
        });
        choosePic.setOnClickListener(view -> showImagePickerDialog());
        setUserData();
    }

    private void showImagePickerDialog() {
        PickImageDialogFragment pickImageDialogFragment = new PickImageDialogFragment();
        pickImageDialogFragment.setPickImageActions(this);
        pickImageDialogFragment.show(getChildFragmentManager(), PickImageDialogFragment.class.getSimpleName());
    }

    private boolean checkIfDataIsValid() {
        firstNameString = firstNameEditText.getText().toString();
        lastNameString = lastNameEditText.getText().toString();
        phoneNumberString = phoneNumberEditText.getText().toString();

        if (firstNameString.isEmpty()) {
            firstNameEditText.setError(getString(R.string.enter_first_name));
            return false;
        }
        if (lastNameString.isEmpty()) {
            lastNameEditText.setError(getString(R.string.enter_last_name));
            return false;
        }

        if (phoneNumberString.isEmpty()) {
            phoneNumberEditText.setError(getString(R.string.enter_phone_number));
            return false;
        }
        return true;
    }

    private void setUserData() {
        User user = editProfilePresenter.getUser(PrefUtils.getUserId(getContext()));
        if (user != null) {
            if (user.getFirstName() != null) {
                firstNameEditText.setText(user.getFirstName());
            }

            if (user.getLastName() != null) {
                lastNameEditText.setText(user.getLastName());
            }

            if (user.getUserPhone() != null) {
                phoneNumberEditText.setText(user.getUserPhone());
            }
        }
    }

    private void proceed() {
        UserModel userModel = new UserModel();
        userModel.setFirstName(firstNameString);
        userModel.setLastName(lastNameString);
        userModel.setUserPhone(phoneNumberString);

        editProfilePresenter.editProfile(userModel, imageFile);
    }

    @Override
    public void showMessage(String message) {
        showToastMessage(message);
    }

    @Override
    public void showMessage(int resID) {
        showToastMessage(resID);
    }

    @Override
    public void showLoading() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null)
            progressDialog.hide();
    }

    @Override
    public void setError(EditText editText, int resId) {

    }

    @Override
    public void finish() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void onGalleryClicked() {
        editProfilePresenter.pickImageFromGallery(this);
    }

    @Override
    public void onCameraClicked() {
        editProfilePresenter.pickImageFromCamera(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                EditProfileFragment.this.imageFile = imageFile;
                GlideApp.with(getContext()).load(imageFile).circleCrop().into(profilePictureImageView);
                GlideApp.with(getContext()).asBitmap().listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e,
                                                Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        Bitmap blurredBitmap = GaussianBlur.with(getContext()).render(resource);
                        profilePictureBackgroundImageView.setImageBitmap(blurredBitmap);
                        return true;
                    }
                }).load(imageFile).into(profilePictureBackgroundImageView);

            }
        });
    }

}
