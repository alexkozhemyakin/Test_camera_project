package com.sizer.mvp.view;


import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface RegisterView extends BaseView {
    @StateStrategyType(SingleStateStrategy.class)
    void onSuccess();
}
