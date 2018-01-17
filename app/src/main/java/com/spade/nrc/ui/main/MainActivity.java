package com.spade.nrc.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.spade.nrc.R;
import com.spade.nrc.ui.explore.view.ExploreFragment;
import com.spade.nrc.ui.presenters.view.PresentersFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        openExploreFragment();
    }

    private void openExploreFragment() {
        ExploreFragment exploreFragment = new ExploreFragment();
        openFragment(exploreFragment, "");
    }


    private void openFragment(Fragment fragment, String title) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
