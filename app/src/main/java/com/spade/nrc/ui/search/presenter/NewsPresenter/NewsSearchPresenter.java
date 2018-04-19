package com.spade.nrc.ui.search.presenter.NewsPresenter;

import com.spade.nrc.ui.search.view.NewsSearch.NewsSearchFragmentView;
import com.spade.nrc.ui.search.view.presentersSearch.PresentersSearchView;

public interface NewsSearchPresenter {

     void setView(NewsSearchFragmentView view) ;

     void findNews(String key);

}
