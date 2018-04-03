package com.spade.nrc.ui.search.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.general.PagingAdapter;
import com.spade.nrc.ui.search.presenter.SearchFragmentPresenter;
import com.spade.nrc.ui.search.presenter.SearchFragmentPresenterImpl;

import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class SearchFragment extends BaseFragment  implements SearchFragmentView{


    private View mainView;
    private PagingAdapter pagingAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SearchFragmentPresenter fragmentSearchPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
        viewPager=mainView.findViewById(R.id.search_viewpager);
        tabLayout=mainView.findViewById(R.id.search_tabs);

    }

    @Override
    protected void initPresenter() {
        fragmentSearchPresenter=new SearchFragmentPresenterImpl(this);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }




    private void setupViewPager(ViewPager viewPager) {
        pagingAdapter = new PagingAdapter(getChildFragmentManager());
        pagingAdapter.addFragment(fragmentSearchPresenter.getViewPagerFragment(),fragmentSearchPresenter.getViewPagerFragmentsTitles());
        viewPager.setAdapter(pagingAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagingAdapter);
        viewPager.setOffscreenPageLimit(4);

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
