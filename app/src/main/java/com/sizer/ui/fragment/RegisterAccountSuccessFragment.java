package com.sizer.ui.fragment;

import android.content.Intent;

import com.sizer.R;
import com.sizer.ui.activity.SplashActivity;
import com.sizer.ui.activity.VideoActivity;

import butterknife.OnClick;

public class RegisterAccountSuccessFragment extends BaseFragment {


    @OnClick(R.id.btn_done)
    void doneAction() {
        getActivity().startActivity(new Intent(getActivity(), SplashActivity.class));
        getActivity().finish();

    }

    @Override
    int getLayoutResource() {
        return R.layout.account_success_register;
    }


}
