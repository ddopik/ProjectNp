package com.spade.nrc.ui.search.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.general.PagingAdapter;
import com.spade.nrc.ui.search.presenter.SearchFragmentPresenter;
import com.spade.nrc.ui.search.presenter.SearchFragmentPresenterImpl;
import com.spade.nrc.utils.ChannelUtils;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class SearchFragment extends BaseFragment implements SearchFragmentView {


    private int searchId = 4;
    private View mainView;
    private PagingAdapter pagingAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private EditText searchAction;
    private SearchFragmentPresenter fragmentSearchPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.fragment_search, container, false);
        initViews();
        setupViewPager(viewPager);
        return mainView;
    }

    @Override
    protected void initViews() {
        viewPager = mainView.findViewById(R.id.search_viewpager);
        tabLayout = mainView.findViewById(R.id.search_tabs);
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), ChannelUtils.getChannelDetailsColorList(searchId)));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), ChannelUtils.getChannelSecondaryColor(searchId)));
        searchAction = mainView.findViewById(R.id.search_action);


//        searchAction.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //Do something here
//                fragmentSearchPresenter.notifyFragment(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        mgr.showSoftInput(searchAction, InputMethodManager.SHOW_IMPLICIT);

//        searchAction.setFocusableInTouchMode(true);
//        searchAction.setOnClickListener(v -> {
//            searchAction.requestFocus();
//        });
//        searchAction.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    fragmentSearchPresenter.notifyFragment(v.getText().toString());
//                    return true;
//                }
//                return false;
//            }
//        });

    }

    @Override
    protected void initPresenter() {
        fragmentSearchPresenter = new SearchFragmentPresenterImpl(this);
    }


    private void setupViewPager(ViewPager viewPager) {
        pagingAdapter = new PagingAdapter(getFragmentManager());
        pagingAdapter.addFragment(fragmentSearchPresenter.getViewPagerFragment(), fragmentSearchPresenter.getViewPagerFragmentsTitles());
        viewPager.setAdapter(pagingAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setOffscreenPageLimit(3);


    }

    @Override
    public void viewShowSearch() {

    }

    @Override
    public void showToastMessage(String message) {
        super.showToastMessage(message);
    }

    @Override
    public void showToastMessage(int messageResID) {
        super.showToastMessage(messageResID);
    }
}
