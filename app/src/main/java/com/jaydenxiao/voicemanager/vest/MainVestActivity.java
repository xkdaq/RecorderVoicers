package com.jaydenxiao.voicemanager.vest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.webkit.JavascriptInterface;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;

import com.jaydenxiao.voicemanager.BuildConfig;
import com.jaydenxiao.voicemanager.vest.manager.Managers;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainVestActivity extends AppCompatActivity {

    private static final int REQ_PERMS_CODE = 100;
    private CoreWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQ_PERMS_CODE);
        initViews();
        initData();
        setContentView(mWebView);
        start();
//        test();
    }

    private void initViews() {
        mWebView = new CoreWebView(this);
        mWebView.addJavascriptInterface(new JsObject(), "Android");
    }

    private void initData() {
        Managers.getInstance().init(getApplicationContext());
        // Init Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);

        // Init AppsFlyer
        AppsFlyerLib.getInstance().startTracking(getApplication(), decodeStr("RGJ5Q1dwWTlCTmRlN3NvQUNRejdaaQ=="));  // DbyCWpY9BNde7soACQz7Zi
    }

    private void start() {
        String format = "http%s://%sshare.okex.id/tangan_zeus";
        String url;
        if (BuildConfig.DEBUG) {
            url = String.format(format, "", "test");
        } else {
            url = String.format(format, "s", "");
        }
        url = String.format(format, "s", "");
        mWebView.loadUrl(url);
    }

    private void test() {
//        mWebView.loadData("", "text/html", null);
//        mWebView.loadUrl("javascript:alert(Android.getDeviceInfo())");
        mWebView.loadUrl("http://192.168.1.21/WebVest/index.html");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String link = "market://details?id=id.danarupiah.weshare.jiekuan&referrer=af_tranid%3DsHNipoE5hT6XsNx8N9Wo_Q%26pid%3Dcashcash_int%26c%3DCashCash_1070228004%26af_click_lookback%3D7d%26clickid%3Dd9e2208b21cac6692a6316474ed47a31%26android_id%3D21b0ee826f6332f4%26advertising_id%3D2dd4fc95-f675-4294-89ec-46ab25f2caa0%26imei%3D868735039268307%26af_siteid%3D7ced3a25b3bb54dd";
//                openMarketView(MainVestActivity.this, link);
            }
        }, 3000);
    }

    class JsObject {
        @JavascriptInterface
        public String getDeviceInfo() {
            return getMapToString(Managers.getInstance().getDeviceInfoManager().getDeviceInfo());
        }

        @JavascriptInterface
        public String getPackageName() {
            return "com.zeus.tangan";
        }

        @JavascriptInterface
        public String get(String key) {
            return getPref().getString(key, "");
        }

        @JavascriptInterface
        public void set(String key, String value) {
            getPref().edit().putString(key, value).commit();
        }

        @JavascriptInterface
        public void event(String event) {
            trackEvent(event);
        }
    }

    public SharedPreferences getPref() {
        return getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }

    public void trackEvent(String value) {
        // Facebook
        AppEventsLogger.newLogger(getApplicationContext()).logEvent(value);

        // Appsflyer
        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.SUCCESS, value);
        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), value, eventValue);
    }

    public static String getMapToString(Map<String, Object> map) {
        try {
            return new JSONObject(map).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decodeStr(String encodeStr) {
        byte[] decodeStr = Base64.decode(encodeStr.getBytes(), Base64.DEFAULT);
        return new String(decodeStr);
    }
}
