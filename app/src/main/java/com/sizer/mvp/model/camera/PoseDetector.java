package com.sizer.mvp.model.camera;


import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.sizer.model.Pose;
import com.sizer.util.Constants;
import com.sizer.util.ImageUtil;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

import static com.sizer.util.Constants.FRAME_HEIGHT;
import static com.sizer.util.Constants.FRAME_WIDTH;

/**
 * Extends GMS Detector. Detects Poses from every Frame
 */
public class PoseDetector extends Detector<Pose> {
    private final static String TAG = "POSE DETECTOR";

    private PoseListener listener;
    private int numFrame;

    private boolean isFront = true;

    public PoseDetector() {
        numFrame = 0;
        //this.listener = listener;
    }

    public void setFacing(boolean front) {
        isFront = front;
    }

    public void setListener(PoseListener listener)
    {
        this.listener = listener;
    }

    public void reset() {
        numFrame = 0;
    }

    @Override
    public SparseArray<Pose> detect(Frame frame) {
        long duration = System.currentTimeMillis();
        if (numFrame < Constants.NUM_FRAMES)
        {
            if(listener!=null) {
                YuvImage yuvImage = new YuvImage(ImageUtil.rotateNV21(frame.getGrayscaleImageData().array(), frame.getMetadata().getWidth(), frame.getMetadata().getHeight(),isFront?270:90, isFront), ImageFormat.NV21, FRAME_WIDTH, FRAME_HEIGHT, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                yuvImage.compressToJpeg(new Rect(0, 0, FRAME_WIDTH, FRAME_HEIGHT), 75, baos);
                byte[] jpegArray = baos.toByteArray();
                listener.onPoseImage(jpegArray, numFrame);
            }
        }
        Pose pose = new Pose();
        SparseArray<Pose> poses = new SparseArray<>();
        poses.append(0,pose);
        Log.d(TAG, "on DETECT");
        duration = System.currentTimeMillis() - duration;
        Log.d(TAG, "duration " +String.valueOf(duration));
        try {
            if(duration<1000)
                TimeUnit.MILLISECONDS.sleep(1000-duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        numFrame++;
        return poses;
    }

}