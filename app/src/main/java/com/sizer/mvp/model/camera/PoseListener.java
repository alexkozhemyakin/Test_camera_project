package com.sizer.mvp.model.camera;

public interface PoseListener {
    void onPoseImage(byte[] jpeg, int numFrame);
}
