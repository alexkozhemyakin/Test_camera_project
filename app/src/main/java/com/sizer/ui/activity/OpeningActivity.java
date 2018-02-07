package com.sizer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.sizer.App;
import com.sizer.R;
import com.sizer.model.ApiResponse;
import com.sizer.model.Version;
import com.sizer.mvp.model.IRemoteRepository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpeningActivity extends BaseActivity {
    private final String RESULT_CODE_OK = "OK";
    private final String RESULT_CODE_ERROR = "ERROR";

    private IRemoteRepository remoteRepository;
    @BindView(R.id.btn_start)
    View startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        remoteRepository = App.getAppComponent().remoteDataRepository();
        if (isInternetPermissionGranted() && isCameraPermissionGranted() && isWriteExternalStoragePermissionGranted()) {
            checkVersion();
        } else {
            //TODO move CAMERA and WRITE_EXTERNAL_STORAGE to Fragment before button click?
            String[] permissions = {
                    Manifest.permission.INTERNET,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST);
        }
    }

    @OnClick(R.id.btn_start)
    void onBtnStart() {
        startActivity(new Intent(this, ScanActivity.class));
    }

    @OnClick(R.id.btn_debug)
    void onBtnDebug() {
        showMessage("Not implemented yet.");
    }

    @Override
    int getContentViewLayoutResource() {
        return R.layout.activity_opening;
    }

    private void checkVersion() {
        remoteRepository.getVersion().enqueue(new Callback<ApiResponse<Version>>() {
            @Override
            public void onResponse(Call<ApiResponse<Version>> call, Response<ApiResponse<Version>> response) {
                ApiResponse<Version> body = response.body();
                if (body != null && RESULT_CODE_OK.equals(body.getResultCode())) {
                    showMessage("Version accepted");
                    startBtn.setEnabled(true);

                } else if (body == null || RESULT_CODE_ERROR.equals(body.getResultCode())) {
                    showMessage("Failure");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Version>> call, Throwable t) {
                showMessage("Failure");
            }
        });
    }
}
