package com.spade.nrc.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by Ayman Abouzeid on 10/30/17.
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    protected abstract void initPresenter();

    protected abstract void initViews();

    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void showToastMessage(int messageResID) {
        Toast.makeText(getContext(), getString(messageResID), Toast.LENGTH_LONG).show();
    }
}
