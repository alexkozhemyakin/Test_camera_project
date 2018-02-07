package com.sizer.model;

public class Pose {
    private int poseID;
    private String currentPose;
    private int errorID;
    private String errorStr;
    private String frameName;
    private boolean shouldSave;

    public int getPoseID() {
        return poseID;
    }

    public void setPoseID(int poseID) {
        this.poseID = poseID;
    }

    public String getCurrentPose() {
        return currentPose;
    }

    public void setCurrentPose(String currentPose) {
        this.currentPose = currentPose;
    }

    public int getErrorID() {
        return errorID;
    }

    public void setErrorID(int errorID) {
        this.errorID = errorID;
    }

    public String getErrorStr() {
        return errorStr;
    }

    public void setErrorStr(String errorStr) {
        this.errorStr = errorStr;
    }

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    public boolean isShouldSave() {
        return shouldSave;
    }

    public void setShouldSave(boolean shouldSave) {
        this.shouldSave = shouldSave;
    }

}
