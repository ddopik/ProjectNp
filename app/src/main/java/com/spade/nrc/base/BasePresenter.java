package com.spade.nrc.base;

/**
 * Created by Ayman Abouzeid on 10/30/17.
 */

public interface BasePresenter<V extends BaseView> {
    void setView(V view);
}
