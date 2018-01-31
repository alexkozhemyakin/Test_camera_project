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
import com.sizer.ui.fragment.StartedFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    private StartedFragment fragment;
    //TODO need use bind?
    View v;
    private IRemoteRepository remoteRepository;

    @Override
    int getContentViewLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        v = findViewById(android.R.id.content);
        fragment = new StartedFragment();
        remoteRepository = App.getAppComponent().remoteDataRepository();

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                //TODO ?
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        7);
            }
        }
        else {
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
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 7: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchVersion();

                } else {
                    //TODO ?
                }
                return;
            }
        }
    }

    private void fetchVersion() {
        remoteRepository.getVersion().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                showMessage("Version accepted");
                setGettingStarted();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showMessage("Version accepted");
                setGettingStarted();
            }
        });
    }
}
