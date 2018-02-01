package com.sizer.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;

import com.sizer.App;
import com.sizer.R;
import com.sizer.data.IRemoteRepository;
import com.sizer.model.ApiResponse;
import com.sizer.model.Version;
import com.sizer.ui.fragment.SplashStartedFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    private final String RESULT_CODE_OK = "OK";
    private final String RESULT_CODE_ERROR = "ERROR";

    private SplashStartedFragment fragment;
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
        fragment = new SplashStartedFragment();
        remoteRepository = App.getAppComponent().remoteDataRepository();

        int permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.INTERNET}, 7);
            }
        } else {
            fetchVersion();
        }
    }

    public void setGettingStarted() {
        FrameLayout lyt = (FrameLayout) findViewById(R.id.frame_splash);
        lyt.removeAllViews();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_out, R.anim.fade_in);

        ft.replace(R.id.frame_splash, fragment);
        ft.commit();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 7) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
