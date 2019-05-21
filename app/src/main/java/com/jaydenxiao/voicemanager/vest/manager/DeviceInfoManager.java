package com.jaydenxiao.voicemanager.vest.manager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.jaydenxiao.voicemanager.vest.utils.AdvertisingIdClient;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.TELEPHONY_SERVICE;

public class DeviceInfoManager {

    private Context mContext;
    private String mAndroidId = "";
    private String mGaid = "";
    private String mImei = "";
    private String mMac = "";

    public DeviceInfoManager(Context context) {
        assert (context != null);
        mContext = context;
        init();
    }

    private void init() {
        mAndroidId = getAndroidID(mContext);
        getGaid();
//        mImei = getImei(mContext);
//        mMac = getMacAddress(mContext);
    }

    public Map<String, Object> getDeviceInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("and_id", mAndroidId);
            map.put("gaid", mGaid);
            map.put("imei", mImei);
            map.put("mac", mMac);
            map.put("sn", getSerialNumber());
            map.put("model", getModel());
            map.put("brand", getBrand());
            map.put("release", getReleaseVersion());
            map.put("sdk_version", getSdkVersion());
//            map.put("is_root", DriverInfoUtil.isRooted() ? "1" : "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private String getAndroidID(Context context) {
        return android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    private void getGaid() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    AdvertisingIdClient.AdInfo adInfo = AdvertisingIdClient.getAdvertisingIdInfo(mContext);
                    mGaid = adInfo.getId();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static String getImei(Context context) {
        String imei = "";
        if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            imei = getTelephonyManager(context).getDeviceId();
        }
        return imei;
    }

    public static String getSerialNumber() {
        return Build.SERIAL;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getReleaseVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getSdkVersion() {
        return Build.VERSION.SDK;
    }

    public static String getMacAddress(Context context) {
        String mac = "";
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            mac = wifi.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mac;
    }

    public static TelephonyManager getTelephonyManager(Context context) {
        return (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
    }

    public static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
