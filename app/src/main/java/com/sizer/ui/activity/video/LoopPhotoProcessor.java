package com.sizer.ui.activity.video;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;

import java.util.HashSet;
import java.util.Set;

public abstract class LoopPhotoProcessor extends CameraListener {

    public static final String TAG = LoopPhotoProcessor.class.getSimpleName() + "======>";
    public static final int CHECK_PHOTO_TASK_STATUS_INTERVAL_MILLS = 1000;

    private Set<SavePhotoTask> savePhotoTasks = new HashSet<>();
    private Runnable takePictureRunnable = () -> {
        getCameraView().capturePicture();
    };

    protected abstract boolean needTakeOneMorePhoto(int pictureAlreadyTakenAmount);

    protected abstract CameraView getCameraView();

    protected abstract int getShutterPeriodInMills();

    protected abstract void allPhotosProcessed();

    @Override
    public void onPictureTaken(byte[] jpeg) {
        super.onPictureTaken(jpeg);
        Log.d(TAG, "onPictureTaken");
        SavePhotoTask task = new SavePhotoTask(savePhotoTasks.size());
        task.execute(jpeg);
        savePhotoTasks.add(task);


        if (needTakeOneMorePhoto(savePhotoTasks.size())) {
            waitingAndTakePicture();
        } else {
            waitingAllAsyncComplete();
        }
    }

    @Override
    public void onCameraClosed() {
        Log.d(TAG, "onCameraClosed");
        stopAllTask();
        super.onCameraClosed();
    }

    @Override
    public void onCameraError(@NonNull CameraException exception) {
        Log.d(TAG, "onCameraError");
        stopAllTask();
        super.onCameraError(exception);
    }

    private void waitingAndTakePicture() {
        Log.d(TAG, "waitingAndTakePicture: " + savePhotoTasks.size());
        getCameraView().getHandler().postDelayed(takePictureRunnable, getShutterPeriodInMills());
    }

    private void waitingAllAsyncComplete() {
        for (SavePhotoTask task : savePhotoTasks) {
            if (task.isAlive()) {
                Log.d(TAG, "waitingAllAsyncComplete: wait");
                getCameraView().getHandler().postDelayed(this::waitingAllAsyncComplete, CHECK_PHOTO_TASK_STATUS_INTERVAL_MILLS);
                return;
            }
        }
        Log.d(TAG, "waitingAllAsyncComplete: done");
        allPhotosProcessed();
    }

    public void startProcessPhoto() {
        Log.d(TAG, "startProcessPhoto");
        takePictureRunnable.run();
    }

    private void stopAllTask() {
        Log.d(TAG, "stopAllTask run");
        getCameraView().getHandler().removeCallbacksAndMessages(takePictureRunnable);
        for (SavePhotoTask task : savePhotoTasks) {
            if (task.isAlive()) {
                task.cancel(false);
            }
        }
        savePhotoTasks.clear();
    }
}
