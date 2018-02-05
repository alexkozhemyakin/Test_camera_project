package com.sizer.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sizer.ui.activity.BaseActivity;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    abstract int getLayoutResource();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

}
