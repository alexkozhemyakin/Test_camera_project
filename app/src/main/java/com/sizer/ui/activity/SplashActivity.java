package com.sizer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.sizer.App;
import com.sizer.R;
import com.sizer.mvp.model.IRemoteRepository;
import com.sizer.model.ApiResponse;
import com.sizer.model.Version;
import com.sizer.ui.fragment.SplashStartedFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    private final String RESULT_CODE_OK = "OK";
    private final String RESULT_CODE_ERROR = "ERROR";

    private SplashStartedFragment fragment = new SplashStartedFragment();
    private View v;
    private IRemoteRepository remoteRepository;

    @Override
    int getContentViewLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = findViewById(android.R.id.content);
        remoteRepository = App.getAppComponent().remoteDataRepository();

        if (isInternetPermissionGranted() && isCameraPermissionGranted() && isWriteExternalStoragePermissionGranted()) {
            fetchVersion();
        } else {
//            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            //TODO move CAMERA and WRITE_EXTERNAL_STORAGE to Fragment before button click?
            String[] permissions = {
                    Manifest.permission.INTERNET,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
            ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST);
//            }
        }
    }

    public void setGettingStarted() {
        startActivity(new Intent(this, OpeningActivity.class));
        finish();
        /*//TODO use butterknife
        FrameLayout lyt = (FrameLayout) findViewById(R.id.frame_splash);
        lyt.removeAllViews();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
//        ft.setCustomAnimations(R.anim.fade_out, R.anim.fade_in);

        ft.replace(R.id.frame_splash, fragment);
        ft.commitAllowingStateLoss();*/


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST) {
            // If request is cancelled, the result arrays are empty.
            if (isInternetPermissionGranted()) {
                fetchVersion();
            }
        }
    }

    private void fetchVersion() {
        remoteRepository.getVersion().enqueue(new Callback<ApiResponse<Version>>() {
            @Override
            public void onResponse(Call<ApiResponse<Version>> call, Response<ApiResponse<Version>> response) {
                ApiResponse<Version> body = response.body();
                if (body != null && RESULT_CODE_OK.equals(body.getResultCode())) {
                    showMessage("Version accepted");
                    setGettingStarted();
                } else if (body == null || RESULT_CODE_ERROR.equals(body.getResultCode())) {
                    showMessage("Failure");
                    // TODO: 2/1/18 add behaviour
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Version>> call, Throwable t) {
                showMessage("Failure");
                // TODO: 2/1/18 add behaviour
            }
        });
    }
}
