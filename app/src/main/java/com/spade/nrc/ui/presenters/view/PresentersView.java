package com.spade.nrc.ui.presenters.view;

import com.spade.nrc.base.BaseView;
import com.spade.nrc.ui.presenters.model.Presenter;

import java.util.List;

/**
 * Created by Ayman Abouzeid on 12/12/17.
 */

public interface PresentersView extends BaseView {
    void showPresenters(List<Presenter> presenters);
}
