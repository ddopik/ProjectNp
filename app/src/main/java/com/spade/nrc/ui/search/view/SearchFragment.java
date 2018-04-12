package com.spade.nrc.ui.search.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.spade.nrc.R;
import com.spade.nrc.base.BaseFragment;
import com.spade.nrc.ui.general.PagingAdapter;
import com.spade.nrc.ui.search.presenter.mainSearchPresenter.SearchPresenter;
import com.spade.nrc.ui.search.presenter.mainSearchPresenter.SearchPresenterImpl;

import java.util.List;

/**
 * Created by abdalla-maged on 4/3/18.
 */

public class SearchFragment extends BaseFragment implements SearchView {


    private View mainView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private android.support.v7.widget.SearchView searchAction;
    private SearchPresenter fragmentSearchPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_search, container, false);
        initViews();
        return mainView;
    }

    @Override
    protected void initViews() {
        ImageView backButton = mainView.findViewById(R.id.back_button);
        searchAction = mainView.findViewById(R.id.search_action);
        viewPager = mainView.findViewById(R.id.search_viewpager);
        tabLayout = mainView.findViewById(R.id.search_tabs);
        tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.nrc_text_color_list));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.nrc_color_list));
        initListener();
        fragmentSearchPresenter.setUpViewPager();
        backButton.setOnClickListener(view -> getActivity().onBackPressed());
    }

    @Override
    protected void initPresenter() {
        fragmentSearchPresenter = new SearchPresenterImpl(getContext());
        fragmentSearchPresenter.setView(this);
    }


    public void initListener() {
        searchAction.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fragmentSearchPresenter.notifyFragment(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
//        searchAction.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//                fragmentSearchPresenter.notifyFragment(searchAction.getText().toString());
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//        });

//            searchAction.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    Toast.makeText(getContext(),"ooo",Toast.LENGTH_SHORT).show();
//
//                }
//            });
    }


    @Override
    public void showMessage(String message) {
        showToastMessage(message);
    }

    @Override
    public void showMessage(int messageResID) {
        showToastMessage(messageResID);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setError(EditText editText, int resId) {

    }

    @Override
    public void addFragment(List<Fragment> fragmentList, List<String> fragmentTitles) {
        PagingAdapter pagingAdapter = new PagingAdapter(getFragmentManager());
        pagingAdapter.addFragment(fragmentList, fragmentTitles);
        viewPager.setAdapter(pagingAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
