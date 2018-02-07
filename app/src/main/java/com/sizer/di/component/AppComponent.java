package com.sizer.di.component;

import android.content.Context;

import com.sizer.mvp.model.ICameraManager;
import com.sizer.mvp.model.camera.PoseDetector;
import com.sizer.mvp.model.camera.PoseProcessor;
import com.sizer.di.module.DetectorModule;
import com.sizer.mvp.model.ILocalRepository;
import com.sizer.mvp.model.IRemoteRepository;
import com.sizer.di.module.AppModule;
import com.sizer.di.module.DataModule;
import com.sizer.di.module.NetModule;
import com.sizer.mvp.presenter.RegisterPresenter;
import com.sizer.mvp.presenter.ScanPresenter;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Component(modules = {AppModule.class, NetModule.class, DataModule.class, DetectorModule.class})
@Singleton
public interface AppComponent {
    Context context();
    ILocalRepository localDataRepository();
    IRemoteRepository remoteDataRepository();
    ICameraManager cameraManager();
    PoseDetector detector();
    PoseProcessor processor();
    Retrofit retrofit();

    void inject(ScanPresenter presenter);
    void inject(RegisterPresenter presenter);
}
