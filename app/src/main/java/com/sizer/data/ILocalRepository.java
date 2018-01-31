package com.sizer.data;

import com.sizer.model.ScanData;
import com.sizer.model.entity.SizerUser;

public interface ILocalRepository {

    ScanData setScanData(ScanData data);
    ScanData getScanData();

    void setSizerUser(SizerUser sizerUser);
    SizerUser getSizerUser();

    String getUniqueDeviceId();
}
