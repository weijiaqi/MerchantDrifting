package com.merchant.drifting.view.customWebview;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class CommWebViewClient extends WebViewClient {
    private WebLoadingListener loadingListener;
    private boolean mIsLoadCurrtenView;

    public CommWebViewClient(WebLoadingListener loadingListener, boolean isLoadCurrtenView) {
        this.loadingListener = loadingListener;
        mIsLoadCurrtenView = isLoadCurrtenView;
    }


    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }

        if (url.startsWith("http:") || url.startsWith("https:")) {
            return false;
        }
        return true;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (loadingListener != null) {
            loadingListener.onLoadStart();
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (loadingListener != null) {
            loadingListener.onLoadFinish();
        }
        view.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                "objs[i].onclick=function(){window.injectedObject.imageClick(this.getAttribute(\"src\"));}" +
                "}" +
                "})()");
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        handler.proceed();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return;
        }
        if (loadingListener != null) {
            loadingListener.onLoadError();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError
            error) {
        super.onReceivedError(view, request, error);
        if (request.isForMainFrame()) {
            if (loadingListener != null) {
                loadingListener.onLoadError();
            }
        }

    }
}
