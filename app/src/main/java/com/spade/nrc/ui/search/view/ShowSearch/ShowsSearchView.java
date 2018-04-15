package com.spade.nrc.ui.search.view.ShowSearch;

import com.spade.nrc.base.BaseView;
import com.spade.nrc.ui.shows.model.Show;

import java.util.List;

/**
 * Created by abdalla-maged on 4/4/18.
 */

public interface ShowsSearchView extends BaseView {

    void viewShowsList(List<Show> showList);

    void hideShowsList();
}
