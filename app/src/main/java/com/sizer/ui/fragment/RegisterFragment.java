package com.sizer.ui.fragment;

import com.sizer.R;
import com.sizer.ui.activity.BaseActivity;

import butterknife.OnClick;

public class RegisterFragment extends BaseFragment {


    public RegisterFragment() {
    }

    @OnClick(R.id.btn_save)
    void callGetStarted() {
        ((BaseActivity)getActivity()).showMessage("Not implemented yet.");
    }

    @Override
    int getLayoutResource() {
        return R.layout.account_register;
    }
}
