package com.spade.nrc.ui.search.view.presentersSearch;

import com.spade.nrc.ui.presenters.model.Presenter;
import com.spade.nrc.ui.shows.model.Show;

import java.util.List;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public interface FragmentSearchPresentersView {

    void ViewSearchPresenters(String key);

    void viewPresentersList(List<Presenter> presenters);
    void hidePresentersList();


    void viewStateMessage(String msg);
    void showProgressBar();

    void hideProgressBar();
}
