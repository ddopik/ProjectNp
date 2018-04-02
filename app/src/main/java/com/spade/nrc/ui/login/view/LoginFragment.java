package com.spade.nrc.ui.login.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.login.UserModel;
import com.spade.nrc.ui.login.presenter.LoginPresenter;
import com.spade.nrc.ui.login.presenter.LoginPresenterImpl;
import com.spade.nrc.ui.main.MainActivity;
import com.spade.nrc.utils.Validator;


/**
 * Created by Ayman Abouzeidd on 6/12/17.
 */

public class LoginFragment extends BaseFragment implements LoginView {

    private EditText emailAddressEditText, passwordEditText;
    private String emailAddressString, passwordString;
    private ProgressDialog progressDialog;

    private LoginPresenter mLoginPresenter;
    private View mView;
    private LoginActions loginActions;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        initViews();
        return mView;
    }

    @Override
    protected void initPresenter() {
        mLoginPresenter = new LoginPresenterImpl(this, getContext());
        mLoginPresenter.initLoginManagers(getActivity());
    }

    @Override
    protected void initViews() {
        TextView continueAsGuest = mView.findViewById(R.id.loginAsGuestBtn);
        TextView loginWithFacebook = mView.findViewById(R.id.loginWithFacebookBtn);
        TextView loginWithGoogle = mView.findViewById(R.id.loginWithGoogleBtn);
        TextView signInButton = mView.findViewById(R.id.loginBtn);
        TextView registerButton = mView.findViewById(R.id.registerBtn);
//        ImageView imageView = mView.findViewById(R.id.logo_image_view);

        continueAsGuest.setOnClickListener(v -> mLoginPresenter.loginAsGuest());
        loginWithFacebook.setOnClickListener(v -> mLoginPresenter.loginWithFacebook(this));
        loginWithGoogle.setOnClickListener(v -> mLoginPresenter.loginWithGoogle(this));
        registerButton.setOnClickListener(v -> loginActions.onRegisterClicked());
        emailAddressEditText = mView.findViewById(R.id.email_address_edit_text);
        passwordEditText = mView.findViewById(R.id.password_edit_text);
//        TextView forgetPassword = mView.findViewById(R.id.forget_password_text_view);
        signInButton.setOnClickListener(v -> {
            if (checkIfDataIsValid()) {
                proceed();
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    signInButton.setVisibility(View.VISIBLE);
                } else {
                    signInButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
//        forgetPassword.setOnClickListener(v ->
//                startActivity(ForgetPasswordActivity.getLaunchIntent(getContext())));
//        signInButton.setOnClickListener(v -> startActivity(ServerLoginActivity.getLaunchIntent(getContext())));
        continueAsGuest.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }


    @Override
    public void setError(EditText editText, int resId) {

    }

    @Override
    public void navigateToMainScreen() {
        startActivity(MainActivity.getLaunchIntent(getContext()));
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        super.onStop();
//        mLoginPresenter.disconnectGoogleApiClient();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoginPresenter.disconnectGoogleApiClient();
    }


    public void setLoginActions(LoginActions loginActions) {
        this.loginActions = loginActions;
    }

    private boolean checkIfDataIsValid() {
        emailAddressString = emailAddressEditText.getText().toString();
        passwordString = passwordEditText.getText().toString();

        if (emailAddressString.isEmpty()) {
            emailAddressEditText.setError(getString(R.string.enter_email_address));
            return false;
        }

        if (!Validator.isEmailAddressValid(emailAddressString)) {
            emailAddressEditText.setError(getString(R.string.enter_valid_email_address));
            return false;
        }

        if (passwordString.isEmpty()) {
            passwordEditText.setError(getString(R.string.enter_password));
            return false;
        }
        return true;
    }

    private void proceed() {
        UserModel userModel = new UserModel();
        userModel.setUserEmail(emailAddressString);
        userModel.setPassword(passwordString);
        mLoginPresenter.serverLogin(userModel);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showMessage(int resID) {
        Toast.makeText(getContext(), getString(resID), Toast.LENGTH_LONG).show();

    }


    @Override
    public void showLoading() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.logging_in));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (progressDialog != null)
            progressDialog.hide();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void navigate() {
//        if (type == RegisterActivity.REGISTER_TYPE) {
        startActivity(MainActivity.getLaunchIntent(getContext()));
//        } else if (type == RegisterActivity.CHECKOUT_TYPE) {
//            Intent intent = UserDataActivity.getLaunchIntent(getContext());
//            intent.putExtra(UserDataFragment.EXTRA_DONATE_TYPE, UserDataFragment.EXTRA_PAY_FOR_PRODUCTS);
//            startActivity(intent);
////            startActivity(UserDataActivity.getLaunchIntent(getContext()));
//        } else {
//        }
    }

    public interface LoginActions {
        void onRegisterClicked();
    }
}
