package com.jaydenxiao.voicemanager.vest.manager;

import android.content.Context;

public class Managers {

    private static Managers smInstance;
    private Context mContext;
    private DeviceInfoManager mDeviceInfoManager;

    public static Managers getInstance() {
        if (smInstance == null) {
            synchronized (Managers.class) {
                if (smInstance == null) {
                    smInstance = new Managers();
                }
            }
        }
        return smInstance;
    }

    public void init(Context context) {
        assert (context != null);
        mContext = context;
        mDeviceInfoManager = new DeviceInfoManager(mContext);
    }

    public DeviceInfoManager getDeviceInfoManager() {
        return mDeviceInfoManager;
    }
}
