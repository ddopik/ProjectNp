package com.spade.nrc.ui.search.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.general.PagingAdapter;
import com.spade.nrc.ui.search.presenter.mainSearchPresenter.SearchFragmentPresenter;
import com.spade.nrc.ui.search.presenter.mainSearchPresenter.SearchFragmentPresenterImpl;
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
        initPresenter();
        initListener();
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





    }

        @Override
        protected void initPresenter () {
            fragmentSearchPresenter = new SearchFragmentPresenterImpl(this);
        }


        public void initListener(){
            searchAction.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    fragmentSearchPresenter.notifyFragment(searchAction.getText().toString().trim());
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    fragmentSearchPresenter.notifyFragment(searchAction.getText().toString());
                }
            });

//            searchAction.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    Toast.makeText(getContext(),"ooo",Toast.LENGTH_SHORT).show();
//
//                }
//            });


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
