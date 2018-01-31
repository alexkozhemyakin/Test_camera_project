package com.sizer.ui.activity.video;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.sizer.data.ILocalRepository;
import com.sizer.model.ScanData;
import com.wonderkiln.camerakit.CameraKitImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SavePhotoTask extends AsyncTask<CameraKitImage, String, String> {

    private final int width = 480;
    private final int height = 640;

    private ILocalRepository localRepository;
    private int frameCnt;

    public SavePhotoTask(ILocalRepository localRepository, int frameCnt) {
        this.localRepository = localRepository;
        this.frameCnt = frameCnt;
    }

    @Override
    public String doInBackground(CameraKitImage... cameraKitImageArr) {
        CameraKitImage cameraKitImage = cameraKitImageArr[0];
        Bitmap bmp = cameraKitImage.getBitmap().copy(Bitmap.Config.RGB_565, true);
//        bmp.setWidth(width);
//        bmp.setHeight(height);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        bmp = null;
        byte[] byteArray = stream.toByteArray();

        try {
            stream.close();
        } catch (IOException e) {
            Log.e("Sizer", "Exception in doInBackground", e);
        }

        String photoPath = localRepository.getUniqueDeviceId() + File.separator + localRepository.setScanData(
                new ScanData()).getScanId() + File.separator;
        File scanPath = new File(Environment.getExternalStorageDirectory(), photoPath);
        File photo = new File(scanPath, String.format("%06d", frameCnt) + ".jpg");

        Log.d("Photo", "photo: " + photo.getPath() + " scanPath: " + scanPath);

        scanPath.mkdirs();

        try {
            FileOutputStream fos = new FileOutputStream(photo);
            fos.write(byteArray);
            fos.close();
        } catch (IOException e) {
            Log.e("Sizer", "Exception in doInBackground", e);
        }

        return (null);
    }
}