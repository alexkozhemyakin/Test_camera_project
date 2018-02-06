package com.sizer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.devdoo.rxpermissions.RxPermission;
import com.otaliastudios.cameraview.CameraView;
import com.sizer.R;
import com.sizer.ui.activity.video.LoopPhotoProcessor;

import java.util.Arrays;

import butterknife.BindView;
import rx.Subscription;

public class VideoActivity extends BaseActivity {

    private final int delayMillis = 1000;
    private final int targetPhotoAmount = 2;

    @BindView(R.id.camera)
    CameraView cameraView;
    Subscription subscription;
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
//        if (!isNeededPermissionsGranted()) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_CONTACTS},
//                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//        }
//
        cameraView.addCameraListener(loopPhotoProcessor);
    }

    @Override
    protected void onResume() {
        Log.d(LoopPhotoProcessor.TAG, "onResume");
        super.onResume();
        cameraView.start();
        subscription = RxPermission.with(getFragmentManager())
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    Log.d(LoopPhotoProcessor.TAG, "granted: " + granted);
                    if (granted) {
                        loopPhotoProcessor.startProcessPhoto();
                    } else {
                        VideoActivity.this.finish(); //go back
                    }
                });
    }

    @Override
    protected void onPause() {
        Log.d(LoopPhotoProcessor.TAG, "onPause");
        cameraView.stop();
        subscription.unsubscribe();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        cameraView.destroy();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(LoopPhotoProcessor.TAG, "onRequestPermissionsResult: " + Arrays.toString(permissions));
    }

    private boolean isNeededPermissionsGranted() {
        return isCameraPermissionGranted() && isWriteExternalStoragePermissionGranted();
    }

    private void startNextActivity() {
        startActivity(new Intent(VideoActivity.this, RegisterActivity.class));
        finish();
    }
}
