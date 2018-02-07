package com.sizer.mvp.model.camera;


import android.util.Log;

import com.google.android.gms.vision.Detector;
import com.sizer.model.Pose;

/**
 * Extends GMS Detector.Processor. Processes all founded Poses
 */
public class PoseProcessor implements Detector.Processor<Pose> {
    private final static String TAG = "POSE PROCESSOR";

    @Override
    public void release() {
        Log.d(TAG, "release");
    }

    @Override
    public void receiveDetections(Detector.Detections<Pose> detections) {
        Log.d(TAG, "received detections "+ detections.getDetectedItems().size());
    }
}