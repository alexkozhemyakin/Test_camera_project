package com.sizer.di.component;

import android.content.Context;

import com.sizer.data.ILocalRepository;
import com.sizer.data.IRemoteRepository;
import com.sizer.di.module.AppModule;
import com.sizer.di.module.DataModule;
import com.sizer.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Component(modules = {AppModule.class, NetModule.class, DataModule.class})
@Singleton
public interface AppComponent {
    Context context();
    ILocalRepository localDataRepository();
    IRemoteRepository remoteDataRepository();
    Retrofit retrofit();
}
