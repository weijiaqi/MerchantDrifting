package com.merchant.drifting.view.customWebview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.fragment.app.FragmentActivity;


/**
 * webView内存泄漏问题
 * 创建时使用代码new 一个对象而并非在xml代码中编写
 * vWeb =  new WebView(getContext().getApplicationContext());
 * container.addView(vWeb);
 * OnDestory 中销毁 WebView
 * <p>
 * if (vWeb != null) {
 * vWeb.setWebViewClient(null);
 * vWeb.setWebChromeClient(null);
 * vWeb.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
 * vWeb.clearHistory();
 * ((ViewGroup) vWeb.getParent()).removeView(vWeb);
 * vWeb.destroy();
 * vWeb = null;
 * }
 **/
public class CustomWebView extends WebView {
    private static boolean imageClickable;

    private boolean mIsLoadCurrtenView;

    public CustomWebView(Context context) {
        super(context);
        initSetting();
    }

    public CustomWebView(Context context, boolean isLoadCurrtenView) {
        super(context);
        initSetting();
        mIsLoadCurrtenView = isLoadCurrtenView;
    }


    //对webview进行设置
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initSetting() {
        WebSettings webSettings = getSettings();
        //支持javaScript
        webSettings.setJavaScriptEnabled(true);
        // AppCache
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        // 文字缩放百分比，默认值 100
        webSettings.setTextZoom(100);
        // 自适应手机屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
        webSettings.setLoadWithOverviewMode(true);
        //允许js_alert弹框
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
        //设置编码
        webSettings.setDefaultTextEncodingName("utf-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        clearCache(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void enAbleDownLoad(FragmentActivity activity) {
        setDownloadListener(new MyWebViewDownLoadListener(activity));
    }



    public void setWebLoadListener(WebLoadingListener webLoadListener) {
        setWebChromeClient(new CommWebChromeClient(webLoadListener));
        setWebViewClient(new CommWebViewClient(webLoadListener, mIsLoadCurrtenView));
    }



    private class MyWebViewDownLoadListener implements DownloadListener {
        private FragmentActivity mActivity;

        public MyWebViewDownLoadListener(FragmentActivity activity) {
            mActivity = activity;
        }

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//            DownloadRequest.whith().downloadWithNotification(mActivity, url);
        }
    }


    @Override
    public void destroy() {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
        removeAllViews();
        stopLoading();
        setWebViewClient(null);
        setWebChromeClient(null);
        loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        clearHistory();
        super.destroy();
    }


}
