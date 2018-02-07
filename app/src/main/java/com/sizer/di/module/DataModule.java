package com.sizer.di.module;


import android.content.Context;

import com.sizer.mvp.model.ICameraManager;
import com.sizer.mvp.model.ILocalRepository;
import com.sizer.mvp.model.IRemoteRepository;
import com.sizer.mvp.model.camera.CameraManager;
import com.sizer.mvp.model.camera.PoseDetector;
import com.sizer.mvp.model.camera.PoseProcessor;
import com.sizer.mvp.model.repository.LocalDataRepository;
import com.sizer.mvp.model.repository.RemoteDataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DataModule {

    @Provides
    @Singleton
    public ICameraManager provideCameraManager(PoseDetector detector, PoseProcessor processor) {
        return new CameraManager(detector, processor);
    }

    @Provides
    @Singleton
    public ILocalRepository provideLocalDataRepository(Context context) {
        return new LocalDataRepository(context);
    }

    @Provides
    @Singleton
    public IRemoteRepository provideRemoteDataRepository(Retrofit retrofit) {
        return new RemoteDataRepository(retrofit);
    }
}
