package com.sizer.mvp.model;

import com.sizer.mvp.model.camera.PoseDetector;
import com.sizer.mvp.model.camera.PoseProcessor;

public interface ICameraManager {

    PoseDetector getDetector();

    PoseProcessor getProcessor();

}
