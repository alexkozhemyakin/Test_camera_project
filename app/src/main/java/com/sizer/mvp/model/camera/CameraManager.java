package com.sizer.mvp.model.camera;

import com.sizer.mvp.model.ICameraManager;

public class CameraManager implements ICameraManager {

    private PoseDetector detector;
    private PoseProcessor processor;

    public CameraManager(PoseDetector detector, PoseProcessor processor)
    {
        this.detector = detector;
        this.processor = processor;
    }

    @Override
    public PoseDetector getDetector() {
        return detector;
    }

    @Override
    public PoseProcessor getProcessor() {
        return processor;
    }

    @Override
    public void resetScan() {
        detector.reset();
    }

}
