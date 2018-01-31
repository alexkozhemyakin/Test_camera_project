package com.sizer.ui.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sizer.R;
import com.sizer.ui.activity.VideoActivity;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartedFragment extends BaseFragment {


    public StartedFragment() {
    }

    @OnClick(R.id.btn_start)
    void callGetStarted() {
        getActivity().startActivity(new Intent(getActivity(), VideoActivity.class));
        getActivity().finish();
    }

    @Override
    int getLayoutResource() {
        return R.layout.fragment_started;
    }
}
