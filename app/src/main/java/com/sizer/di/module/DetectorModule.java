package com.sizer.di.module;

import com.sizer.mvp.model.camera.PoseDetector;
import com.sizer.mvp.model.camera.PoseProcessor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DetectorModule {

    @Provides
    @Singleton
    public PoseDetector provideDetector(){
        return new PoseDetector();
    }

    @Provides
    @Singleton
    public PoseProcessor provideProcessor(){
        return new PoseProcessor();
    }
}
