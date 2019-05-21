package com.jaydenxiao.voicemanager.vest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CoreWebView extends WebView {

    private Context mContext;

    public CoreWebView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        // Settings
        getSettings().setJavaScriptEnabled(true);
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // Cookies
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptFileSchemeCookies(true);
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
        } else {
            CookieManager.getInstance().setAcceptCookie(true);
        }

        // WebViewClient
        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("market://")) {
                    openMarketView(mContext, url);
                    return true;
                }

                view.loadUrl(url);
                return false;
            }
        });

        // WebChromeClient
        setWebChromeClient(new WebChromeClient());

        // Key Listener
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
                    goBack();
                    return true;
                }
                return false;
            }
        });
    }

    private static void openMarketView(Context context, String link) {
        assert (context != null);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(link);
        intent.setData(content_url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        boolean flag = isLocalApp(context, "com.android.vending");
        if (flag) {
            intent.setPackage("com.android.vending");  //指定应用市场
        }
        context.startActivity(intent);
    }

    private static boolean isLocalApp(Context context, String appPkgName) {
        assert (context != null);
        if (TextUtils.isEmpty(appPkgName))
            return false;

        boolean flag = false;
        try {
            if (context.getPackageManager().getLaunchIntentForPackage(appPkgName) != null) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
