package com.sizer.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.sizer.App;
import com.sizer.mvp.model.ICameraManager;
import com.sizer.mvp.model.ILocalRepository;
import com.sizer.mvp.model.camera.PoseDetector;
import com.sizer.mvp.model.camera.PoseListener;
import com.sizer.mvp.view.ScanView;
import com.sizer.util.Constants;

import javax.inject.Inject;

@InjectViewState
public class ScanPresenter extends MvpPresenter<ScanView> {
    public static final String TAG = "SCAN_PRESENTER";

    @Inject
    ICameraManager cameraManager;

    @Inject
    ILocalRepository localRepository;

    public ScanPresenter() {
        App.getAppComponent().inject(this);
        cameraManager.getDetector().setProcessor(cameraManager.getProcessor());
        PoseListener listener = new PoseListener() {
            @Override
            public void onPoseImage(byte[] jpeg, int numFrame) {
                localRepository.saveScan(jpeg, numFrame);
                if(numFrame == Constants.NUM_FRAMES-1)
                    getViewState().onScanFinish();
            }
        };
        cameraManager.getDetector().setListener(listener);
    }

    public PoseDetector getDetector() {
        return cameraManager.getDetector();
    }

    public void callScanReset(){
        cameraManager.getDetector().reset();
    }

    public void callReady() {
        getViewState().onScanStart();
    }


}
