package com.rpolicante.myAppBus.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by policante on 7/31/15.
 */
public class DeviceHelper {
    public static final String getVersionName(Context c){
        PackageInfo pInfo = null;
        try {
            pInfo = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
        } catch (Exception e) {}
        return pInfo.versionName;
    }
}
