package com.spade.nrc.ui.search.view.presentersSearch;

import com.spade.nrc.base.BaseView;
import com.spade.nrc.ui.presenters.model.Presenter;

import java.util.List;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public interface PresentersSearchView extends BaseView{

    void viewPresentersList(List<Presenter> presenters);

    void hidePresentersList();
}
