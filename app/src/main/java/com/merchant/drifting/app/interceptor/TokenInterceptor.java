package com.merchant.drifting.app.interceptor;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jess.arms.base.BaseEntity;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        String respString = source.buffer().clone().readString(Charset.defaultCharset());
        BaseEntity baseEntity = null;
        try {
            baseEntity = new Gson().fromJson(respString, BaseEntity.class);
        } catch (JsonSyntaxException e) {
        }
        if (baseEntity != null && baseEntity.getCode() == 20000) {
       //     Preferences.clearUserLoginData();
//            EventBus.getDefault().post(new UserUnavailableEvent());
        }
        return response;
    }
}
