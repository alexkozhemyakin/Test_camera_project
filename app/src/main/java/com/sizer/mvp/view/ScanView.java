package com.sizer.mvp.view;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface ScanView extends BaseView{
    @StateStrategyType(SingleStateStrategy.class)
    void onScanStart();
    @StateStrategyType(SingleStateStrategy.class)
    void onScanFinish();
}
