package com.sizer.mvp.model.repository;

import static com.sizer.util.Constants.APP_PREFERENCES;
import static com.sizer.util.Constants.PREF_DEBUG_INFO;
import static com.sizer.util.Constants.PREF_USER_EMAIL;
import static com.sizer.util.Constants.PREF_USER_GENDER;
import static com.sizer.util.Constants.PREF_USER_ID;
import static com.sizer.util.Constants.PREF_USER_MANUAL_FOLDER;
import static com.sizer.util.Constants.PREF_USER_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import com.sizer.model.ScanData;
import com.sizer.model.entity.SizerUser;
import com.sizer.mvp.model.ILocalRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;


public class LocalDataRepository implements ILocalRepository {

    private Context context;

    private ScanData data = new ScanData();
    private String deviceId;

    private File scanPath;

    private SizerUser user;

    private SharedPreferences preferences;

    @Override
    public Map<String, byte[]> getScanList() {
        return scanList;
    }

    private Map<String, byte[]> scanList = new HashMap<>();

    public LocalDataRepository(Context context) {
        this.context = context;
        this.deviceId = Settings.Secure.getString(context.getContentResolver(),
            Settings.Secure.ANDROID_ID);
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        user = new SizerUser(preferences.getString(PREF_USER_ID, ""),
            preferences.getString(PREF_USER_EMAIL, ""),
            preferences.getString(PREF_USER_NAME, ""),
            preferences.getString(PREF_USER_GENDER, "male"),
            preferences.getString(PREF_USER_MANUAL_FOLDER, ""));
    }

    @Override
    public ScanData setScanData(ScanData data) {
        this.data = data;
        String str = getUniqueDeviceId() + "/" + getScanData().getScanId() + File.separator;
        scanPath = new File(Environment.getExternalStorageDirectory(), str);
        scanPath.mkdirs();
        scanList.clear();
        return data;
    }

    @Override
    public void setSizerUser(SizerUser user) {
        this.user = user;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_USER_EMAIL, user.getEmail());
        editor.putString(PREF_USER_NAME, user.getName());
        editor.putString(PREF_USER_ID, user.getUserID());
        editor.putString(PREF_USER_GENDER, user.getGender());
        editor.putString(PREF_USER_MANUAL_FOLDER, user.getManualFolder());
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
        return getUniqueDeviceId() + "/" + getScanData().getScanId();
    }

    @Override
    public void setShowDebugInfo(boolean enabled) {
        preferences.edit().putBoolean(PREF_DEBUG_INFO, enabled).apply();
    }

    @Override
    public boolean isShowDebugInfo() {
        return preferences.getBoolean(PREF_DEBUG_INFO, false);
    }

    @Override
    public void saveScan(byte[] jpg, int number) {
        if (number == 0) {
            setScanData(new ScanData());
        }
        String imageId = String.format("%06d", number+1) + ".jpg";
        File photo = new File(scanPath, imageId);
        scanList.put(imageId, jpg);

        try {
            if (!photo.exists()) {
                photo.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(photo);

            fos.write(jpg);
            fos.flush();
            fos.close();
        } catch (java.io.IOException e) {
            Log.e("Sizer", "Exception in photoCallback", e);
        }
    }
}
