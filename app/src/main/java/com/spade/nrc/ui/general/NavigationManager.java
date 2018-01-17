package com.spade.nrc.ui.general;

import android.support.v7.app.AppCompatActivity;

import com.spade.nrc.base.BaseFragment;

/**
 * Created by Ayman Abouzeid on 1/17/18.
 */

public class NavigationManager {

    private AppCompatActivity activity;

    public void openFragmentAsRoot(BaseFragment baseFragment, int fragmentContainerID) {
        activity.getSupportFragmentManager().beginTransaction()
                .add(fragmentContainerID, baseFragment).commit();
    }


    public void openFragment(BaseFragment baseFragment, int fragmentContainerID, String name) {
        activity.getSupportFragmentManager().beginTransaction().
                add(fragmentContainerID, baseFragment).addToBackStack(name).commit();
    }
}
