package com.sizer.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.otaliastudios.cameraview.CameraView;
import com.sizer.R;
import com.sizer.ui.activity.video.LoopPhotoProcessor;

import butterknife.BindView;

public class VideoActivity extends BaseActivity {

    private final int delayMillis = 1000;
    private final int targetPhotoAmount = 2;

    @BindView(R.id.camera)
    CameraView cameraView;
    LoopPhotoProcessor loopPhotoProcessor = new LoopPhotoProcessor() {

        @Override
        protected boolean needTakeOneMorePhoto(int pictureAlreadyTakenAmount) {
            return targetPhotoAmount > pictureAlreadyTakenAmount;
        }

        @Override
        protected CameraView getCameraView() {
            return cameraView;
        }

        @Override
        protected int getShutterPeriodInMills() {
            return delayMillis;
        }

        @Override
        protected void allPhotosProcessed() {
            Log.d(LoopPhotoProcessor.TAG, "allPhotosProcessed");
            startNextActivity();
        }
    };


    @Override
    int getContentViewLayoutResource() {
        return R.layout.activity_video;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraView.addCameraListener(loopPhotoProcessor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isNeededPermissionsGranted()) {
            cameraView.start();
            loopPhotoProcessor.startProcessPhoto();
        } else {
            showMessage("Please add permission for Camera Access and Writing To Storage"); //TODO normal text
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        cameraView.destroy();
        super.onDestroy();
    }

    private boolean isNeededPermissionsGranted() {
        return isCameraPermissionGranted() && isWriteExternalStoragePermissionGranted();
    }

    private void startNextActivity() {
        startActivity(new Intent(VideoActivity.this, RegisterActivity.class));
        finish();
    }
}
