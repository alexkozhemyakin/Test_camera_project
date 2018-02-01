package com.sizer.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.devdoo.rxpermissions.RxPermission;
import com.sizer.App;
import com.sizer.R;
import com.sizer.data.ILocalRepository;
import com.sizer.model.ScanData;
import com.sizer.ui.activity.video.SavePhotoTask;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscription;

public class VideoActivity extends BaseActivity {

    private final int delayMillis = 2000;
    @BindView(R.id.camera)
    CameraView cameraView;
    List<SavePhotoTask> tasks = new ArrayList<>();
    private static int frameCnt;
    Subscription subscription;


    private static ILocalRepository localRepository;

    @Override
    int getContentViewLayoutResource() {
        return R.layout.activity_video;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localRepository = App.getAppComponent().localDataRepository();
        frameCnt = 0;

        cameraView.setFacing(CameraKit.Constants.FACING_FRONT);
        subscription =
                RxPermission.with(getFragmentManager())
                        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) {
                                if (!cameraView.isStarted()) {
//                                    cameraView.setVideoQuality(CameraKit.Constants.VIDEO_QUALITY_480P);
                                    cameraView.setJpegQuality(100);
                                    cameraView.setFocus(CameraKit.Constants.FOCUS_OFF);
                                    cameraView.postDelayed(capturePreview, 1000);
                                    //TODO: or capture captureVideo() call to capture video
                                }
                            }
                        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        subscription.unsubscribe();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        for (SavePhotoTask task : tasks) {
            if (!task.isCancelled()) {
                task.cancel(false);
            }
        }
        super.onDestroy();
    }


    private Runnable capturePreview = new Runnable() {
        @Override
        public void run() {
            cameraView.captureImage(new CameraKitEventCallback<CameraKitImage>() {

                @Override
                public void callback(CameraKitImage cameraKitImage) {
                    frameCnt++;
                    SavePhotoTask task = new SavePhotoTask(localRepository, frameCnt);
                    task.doInBackground(cameraKitImage);
                    tasks.add(task);
                }
            });
            if (frameCnt > 3) {
                cameraView.stopVideo();
                startActivity(new Intent(VideoActivity.this, RegisterActivity.class));
                finish();
            } else {
                // Run again after approximately 1 second.
                cameraView.postDelayed(this, 1000);
            }
        }
    };

    private void captureVideo() {
        String str = localRepository.getUniqueDeviceId() + File.separator + localRepository.setScanData(new ScanData()).getScanId() + File.separator;
        File scanPath = new File(Environment.getExternalStorageDirectory(), str);
        File photo = new File(scanPath, "capture.mpeg");

        scanPath.mkdirs();
        cameraView.captureVideo(photo, new CameraKitEventCallback<CameraKitVideo>() {
            @Override
            public void callback(CameraKitVideo cameraKitVideo) {
                startActivity(new Intent(VideoActivity.this, RegisterActivity.class));
                finish();
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cameraView.stopVideo();
            }
        }, delayMillis);

    }


}
