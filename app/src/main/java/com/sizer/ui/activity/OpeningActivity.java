package com.sizer.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sizer.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpeningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_start)
    void onBtnStart() {
        startActivity(new Intent(this, ScanActivity.class));
    }

    @OnClick(R.id.btn_debug)
    void onBtnDebug() {

    }
}
