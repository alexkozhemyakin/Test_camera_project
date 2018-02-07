package com.sizer.mvp.model.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import com.sizer.model.entity.SizerUser;
import com.sizer.mvp.model.ILocalRepository;
import com.sizer.model.ScanData;

import java.io.File;
import java.io.FileOutputStream;

import static com.sizer.util.Constants.*;


public class LocalDataRepository implements ILocalRepository {
    private Context context;

    private ScanData data = new ScanData();
    private String deviceId;

    private File scanPath;

    private SizerUser user;

    private SharedPreferences preferences;

    public LocalDataRepository(Context context) {
        this.context = context;
        this.deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        user = new SizerUser(preferences.getString(PREF_USER_ID,""),preferences.getString(PREF_USER_EMAIL, ""),
                preferences.getString(PREF_USER_NAME,""),preferences.getString(PREF_USER_GENDER,"male"));
    }

    @Override
    public ScanData setScanData(ScanData data) {
        this.data = data;
        String str = getUniqueDeviceId()+"/"+ getScanData().getScanId()+File.separator;
        scanPath = new File(Environment.getExternalStorageDirectory(), str);
        scanPath.mkdirs();
        return data;
    }

    @Override
    public void setSizerUser(SizerUser user) {
        this.user = user;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USER_EMAIL,user.getEmail());
        editor.putString(PREF_USER_NAME, user.getName());
        editor.putString(PREF_USER_ID, user.getUserID());
        editor.putString(PREF_USER_GENDER, user.getGender());
        editor.apply();
    }

    @Override
    public SizerUser getSizerUser() {
        return user;
    }

    @Override
    public ScanData getScanData() {
        return data;
    }

    @Override
    public String getUniqueDeviceId() {
        return deviceId;
    }

    @Override
    public String getManualFolder() {
        return getUniqueDeviceId()+"/"+getScanData().getScanId();
    }

    @Override
    public void setShowDebugInfo(boolean enabled) {
        preferences.edit().putBoolean(PREF_DEBUG_INFO,enabled).apply();
    }

    @Override
    public boolean isShowDebugInfo() {
        return preferences.getBoolean(PREF_DEBUG_INFO,false);
    }

    @Override
    public void saveScan(byte[] jpg, int number) {
        if(number==0) {
            setScanData(new ScanData());
        }

        File photo=new File(scanPath, String.format("%06d", number)+".jpg");


        try {
            if(!photo.exists())
                photo.createNewFile();
            FileOutputStream fos=new FileOutputStream(photo);

            fos.write(jpg);
            fos.flush();
            fos.close();
        }
        catch (java.io.IOException e) {
            Log.e("Sizer", "Exception in photoCallback", e);
        }
    }
}
