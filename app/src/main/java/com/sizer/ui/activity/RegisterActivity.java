package com.sizer.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.sizer.R;
import com.sizer.ui.fragment.RegisterAccountFragment;

import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Override
    int getContentViewLayoutResource() {
        return R.layout.activity_register;
    }


    @OnClick(R.id.create_account_btn)
    void replaceFragment() {
        FrameLayout lyt = (FrameLayout) findViewById(R.id.activity_register);
        lyt.removeAllViews();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_out, R.anim.fade_in);

        ft.replace(R.id.activity_register, new RegisterAccountFragment());
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
