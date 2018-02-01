package com.sizer.ui.fragment;

import com.sizer.R;
import com.sizer.ui.activity.BaseActivity;

import butterknife.OnClick;

public class RegisterAccountFragment extends BaseFragment {

    @OnClick(R.id.btn_save)
    void callGetStarted() {
        ((BaseActivity)getActivity()).showMessage("Not implemented yet.");
    }

    @Override
    int getLayoutResource() {
        return R.layout.account_register;
    }
}
