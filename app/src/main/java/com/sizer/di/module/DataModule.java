package com.sizer.di.module;


import android.content.Context;

import com.sizer.data.ILocalRepository;
import com.sizer.data.IRemoteRepository;
import com.sizer.data.repo.LocalDataRepository;
import com.sizer.data.repo.RemoteDataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DataModule {
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
