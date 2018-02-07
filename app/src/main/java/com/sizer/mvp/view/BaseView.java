package com.sizer.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


public interface BaseView extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMessage(String msg);
    @StateStrategyType(AddToEndSingleStrategy.class)
    void showLoading(boolean state);
}
