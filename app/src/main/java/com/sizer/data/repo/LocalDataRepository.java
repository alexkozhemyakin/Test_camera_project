package com.sizer.data.repo;

import android.content.Context;
import android.provider.Settings;

import com.sizer.data.ILocalRepository;
import com.sizer.model.ScanData;
import com.sizer.model.SizerUser;


public class LocalDataRepository implements ILocalRepository {
    private Context context;

    private ScanData data = new ScanData();
    private SizerUser sizerUser = new SizerUser();
    private String deviceId;

    public LocalDataRepository(Context context) {
        this.context = context;
        this.deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    @Override
    public ScanData setScanData(ScanData data) {
        this.data = data;
        return data;
    }

    @Override
    public ScanData getScanData() {
        return data;
    }

    @Override
    public void setSizerUser(SizerUser sizerUser) {
        sizerUser = sizerUser;
    }

    @Override
    public SizerUser getSizerUser() {
        return sizerUser;
    }

    @Override
    public String getUniqueDeviceId() {
        return deviceId;
    }
}
