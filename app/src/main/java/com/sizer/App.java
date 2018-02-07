package com.sizer;

import android.app.Application;

import com.sizer.di.component.AppComponent;
import com.sizer.di.component.DaggerAppComponent;
import com.sizer.di.module.AppModule;
import com.sizer.di.module.DataModule;
import com.sizer.di.module.DetectorModule;
import com.sizer.di.module.NetModule;
import com.sizer.util.Constants;


public class App extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .detectorModule(new DetectorModule())
                .dataModule(new DataModule())
                .netModule(new NetModule(Constants.BASE_URL, false))
                .build();
    }
}
