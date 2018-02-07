package com.sizer.ui.activity.video;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.sizer.App;
import com.sizer.mvp.model.ILocalRepository;
import com.sizer.model.ScanData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class SavePhotoTask extends AsyncTask<byte[], String, String> {

    public static final String TAG = LoopPhotoProcessor.TAG;

    private int currentPhotoIndex;

    SavePhotoTask(int currentPhotoIndex) {
        this.currentPhotoIndex = currentPhotoIndex;
    }

    private ILocalRepository getLocalRepository() {
        return App.getAppComponent().localDataRepository();
    }

    @Override
    public String doInBackground(byte[]... jpeg) {
        byte[] byteArray = jpeg[0];
        ILocalRepository localRepository = getLocalRepository();
        String photoPath = localRepository.getUniqueDeviceId() + File.separator + localRepository.setScanData(
                new ScanData()).getScanId() + File.separator;
        File scanPath = new File(Environment.getExternalStorageDirectory(), photoPath);
        File photo = new File(scanPath, String.format("%06d", currentPhotoIndex) + ".jpg");

        Log.d(TAG, "photo: " + photo.getPath() + " scanPath: " + scanPath);

        scanPath.mkdirs();

        try {
            FileOutputStream fos = new FileOutputStream(photo);
            fos.write(byteArray);
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception in doInBackground", e);
        }

        return (null);
    }

    public boolean isAlive() {
        AsyncTask.Status currentStatus = getStatus();
        return currentStatus == AsyncTask.Status.RUNNING || currentStatus == AsyncTask.Status.PENDING;
    }
}