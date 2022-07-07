package com.merchant.drifting.view.customWebview;

import android.os.Build;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class CommWebChromeClient extends WebChromeClient {
    private WebLoadingListener loadingListener;

    public CommWebChromeClient(WebLoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }


    @Override
    public void onPermissionRequest(PermissionRequest request) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            request.grant(request.getResources());
            request.getOrigin();
        }

    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (loadingListener != null) {
            loadingListener.startProgress(newProgress);
        }
    }
}
