package com.spade.nrc.base;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/**
 * Created by Ayman Abouzeid on 10/30/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected abstract void init();

    protected abstract void addFragment();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(int colorID) {
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, colorID));
    }

    public void setActionBarColor(int colorID) {
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, colorID)));
        }
    }
}
