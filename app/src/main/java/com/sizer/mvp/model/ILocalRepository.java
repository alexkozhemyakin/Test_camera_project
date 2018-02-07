package com.sizer.mvp.model;

import com.sizer.model.ScanData;
import com.sizer.model.entity.SizerUser;

import java.util.Map;

public interface ILocalRepository {

    ScanData setScanData(ScanData data);

    ScanData getScanData();

    String getUniqueDeviceId();

    String getManualFolder();

    void setShowDebugInfo(boolean enabled);

    boolean isShowDebugInfo();

    void saveScan(byte[] jpg, int number);

    Map<String, byte[]> getScanList();

    void setSizerUser(SizerUser user);
    SizerUser getSizerUser();
}
