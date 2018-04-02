package com.spade.nrc.ui.register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.login.UserModel;
import com.spade.nrc.ui.main.MainActivity;
import com.spade.nrc.utils.Validator;

/**
 * Created by Ayman Abouzeid on 6/23/17.
 */

public class RegisterFragment extends BaseFragment implements RegisterView {

    private EditText firstNameEditText, lastNameEditText,
            phoneNumberEditText, emailAddressEditText, passwordEditText, confirmPasswordEditText;
    private String firstNameString;
    private String lastNameString;
    private String phoneNumberString;
    private String emailAddressString;
    private String passwordString;
    private ProgressDialog progressDialog;

    private View fragmentView;
    private RegisterPresenter registerPresenter;
//    private int type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        type = getArguments().getInt(RegisterActivity.EXTRA_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_register, container, false);
        initViews();
        return fragmentView;
    }

    @Override
    protected void initPresenter() {
        registerPresenter = new RegisterPresenterImpl(getContext());
        registerPresenter.setView(this);
    }

    @Override
    protected void initViews() {
        firstNameEditText = fragmentView.findViewById(R.id.first_name_edit_text);
        lastNameEditText = fragmentView.findViewById(R.id.last_name_edit_text);
        phoneNumberEditText = fragmentView.findViewById(R.id.phone_number_edit_text);
        emailAddressEditText = fragmentView.findViewById(R.id.email_address_edit_text);
        passwordEditText = fragmentView.findViewById(R.id.password_edit_text);
        confirmPasswordEditText = fragmentView.findViewById(R.id.confirm_password_edit_text);
        TextView proceedBtn = fragmentView.findViewById(R.id.register_btn);
        ImageView backBtn = fragmentView.findViewById(R.id.back_button);
        backBtn.setOnClickListener(view -> getActivity().onBackPressed());
        proceedBtn.setOnClickListener(v -> {
            if (checkIfDataIsValid()) {
                proceed();
            }
        });
    }


    private boolean checkIfDataIsValid() {
        firstNameString = firstNameEditText.getText().toString();
        lastNameString = lastNameEditText.getText().toString();
        emailAddressString = emailAddressEditText.getText().toString();
        phoneNumberString = phoneNumberEditText.getText().toString();
        passwordString = passwordEditText.getText().toString();
        String confirmPasswordString = confirmPasswordEditText.getText().toString();

        if (firstNameString.isEmpty()) {
            firstNameEditText.setError(getString(R.string.enter_first_name));
            return false;
        }
        if (lastNameString.isEmpty()) {
            lastNameEditText.setError(getString(R.string.enter_last_name));
            return false;
        }
        if (emailAddressString.isEmpty()) {
            emailAddressEditText.setError(getString(R.string.enter_email_address));
            return false;
        }

        if (!Validator.isEmailAddressValid(emailAddressString)) {
            emailAddressEditText.setError(getString(R.string.enter_valid_email_address));
            return false;
        }

        if (phoneNumberString.isEmpty()) {
            phoneNumberEditText.setError(getString(R.string.enter_phone_number));
            return false;
        }
        if (passwordString.isEmpty()) {
            passwordEditText.setError(getString(R.string.enter_password));
            return false;
        }

        if (confirmPasswordString.isEmpty()) {
            confirmPasswordEditText.setError(getString(R.string.enter_confirm_password));
            return false;
        }

        if (!passwordString.equals(confirmPasswordString)) {
            confirmPasswordEditText.setError(getString(R.string.password_mismatch));
            return false;
        }
        return true;
    }

    private void proceed() {
        UserModel userModel = new UserModel();
        userModel.setFirstName(firstNameString);
        userModel.setLastName(lastNameString);
        userModel.setUserEmail(emailAddressString);
        userModel.setUserPhone(phoneNumberString);
        userModel.setPassword(passwordString);

        registerPresenter.register(userModel);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(int resID) {
        Toast.makeText(getContext(), resID, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.registering));
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
        getActivity().finish();
    }

    @Override
    public void navigate() {
//        if (type == RegisterActivity.REGISTER_TYPE) {
        startActivity(MainActivity.getLaunchIntent(getContext()));
//        } else if (type == RegisterActivity.CHECKOUT_TYPE) {
////            Intent intent = UserDataActivity.getLaunchIntent(getContext());
////            intent.putExtra(UserDataFragment.EXTRA_DONATE_TYPE, UserDataFragment.EXTRA_PAY_FOR_PRODUCTS);
////            startActivity(intent);
//////            startActivity(UserDataActivity.getLaunchIntent(getContext()));
//        }
    }

}
