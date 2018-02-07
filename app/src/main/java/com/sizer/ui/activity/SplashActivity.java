package com.sizer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.sizer.R;

public class SplashActivity extends BaseActivity {

    View v;
    Handler h;

    private Runnable start = this::startNext;

    @Override
    int getContentViewLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        h = new Handler();
        h.postDelayed(start, 1500);

    }

    public void startNext() {
        startActivity(new Intent(this, OpeningActivity.class));
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        h.removeCallbacks(start);
    }
}
