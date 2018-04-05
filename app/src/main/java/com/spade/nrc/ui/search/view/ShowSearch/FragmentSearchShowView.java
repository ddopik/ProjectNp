package com.spade.nrc.ui.search.view.ShowSearch;

import com.spade.nrc.ui.shows.model.Show;

import java.util.List;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public interface FragmentSearchShowView {

    void ViewSearchShow(String key);


    void viewShowsList(List<Show> showList);
    void hideShowsList();


    void viewStateMessage(String msg);
    void showProgressBar();

    void hideProgressBar();
}
