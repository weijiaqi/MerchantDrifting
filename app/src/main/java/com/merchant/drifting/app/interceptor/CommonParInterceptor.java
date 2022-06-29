package com.merchant.drifting.app.interceptor;

import android.text.TextUtils;


import com.jess.arms.utils.SystemUtil;
import com.merchant.drifting.app.application.MerchantDriftingApplication;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.AppUtil;
import com.merchant.drifting.util.StringUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @description  拦截器
 * @author 卫佳琪1
 * @time 15:06 15:06
 */

public class CommonParInterceptor implements Interceptor {
    //声明响应对象
    private Response response;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String method = originalRequest.method();
        if (TextUtils.equals(method,"POST")){
            RequestBody oldBody = originalRequest.body();
            if (oldBody instanceof FormBody){
                FormBody formBody = (FormBody) originalRequest.body();
                originalRequest = originalRequest.newBuilder().removeHeader("User-Agent")//移除旧的
                        .addHeader("User-Agent", SystemUtil.getUserAgent(MerchantDriftingApplication.getContext()))//添加真正的头部
                        .addHeader("Token", StringUtil.formatNullString(Preferences.getToken()))
                        .addHeader("Version", StringUtil.formatNullString(AppUtil.getVerName(MerchantDriftingApplication.getContext()) + ""))
                        .addHeader("Sign",StringUtil.formatNullString(getSign(Preferences.getPhone())))
                        .addHeader("source","Android")
                        .addHeader("Accept", "application/json")
                        .post(formBody)
                        .build();
                response = chain.proceed(originalRequest);
            }else {
                MultipartBody multipartBody = (MultipartBody) originalRequest.body();
                originalRequest = originalRequest.newBuilder().removeHeader("User-Agent")//移除旧的
                        .addHeader("User-Agent", SystemUtil.getUserAgent(MerchantDriftingApplication.getContext()))//添加真正的头部
                        .addHeader("Token", StringUtil.formatNullString(Preferences.getToken()))
                        .addHeader("Version", StringUtil.formatNullString(AppUtil.getVerName(MerchantDriftingApplication.getContext()) + ""))
                        .addHeader("Sign",StringUtil.formatNullString(getSign(Preferences.getPhone())))
                        .addHeader("source","Android")
                        .addHeader("Accept", "application/json")
                        .post(multipartBody)
                        .build();
                response = chain.proceed(originalRequest);
            }
        }else {
            Request request = new Request.Builder()
                    .removeHeader("User-Agent").addHeader("User-Agent",
                            SystemUtil.getUserAgent(MerchantDriftingApplication.getContext()))
                    .addHeader("Token", StringUtil.formatNullString(Preferences.getToken()))
                    .addHeader("Version", StringUtil.formatNullString(AppUtil.getVerName(MerchantDriftingApplication.getContext()) + ""))
                    .addHeader("Sign",StringUtil.formatNullString(getSign(Preferences.getPhone())))
                    .addHeader("source","Android")
                    .url(originalRequest.url())
                    .build();
            response = chain.proceed(request);
        }
        return response;
    }

    private String  getSign(String phone){
        if (!TextUtils.isEmpty(phone)){
            return StringUtil.md5(phone+"gu940s");
        }else {
            return "";
        }
    }
}
