package com.spade.nrc.ui.general;

import android.support.v7.app.AppCompatActivity;

import com.spade.nrc.base.BaseFragment;

/**
 * Created by Ayman Abouzeid on 1/17/18.
 */

public class NavigationManager {

    private AppCompatActivity activity;

    public NavigationManager(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void openFragmentAsRoot(BaseFragment baseFragment, int fragmentContainerID, String tag) {
        activity.getSupportFragmentManager().beginTransaction()
                .add(fragmentContainerID, baseFragment, tag).commit();
    }


    public void openFragment(BaseFragment baseFragment, int fragmentContainerID, String tag) {
        activity.getSupportFragmentManager().beginTransaction().
                add(fragmentContainerID, baseFragment).addToBackStack(tag).commit();
    }
}
