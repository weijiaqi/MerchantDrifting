package com.merchant.drifting.app.interceptor;




import com.merchant.drifting.util.downloadutil.FileResponseBody;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * create by 卫佳琪
 * on 2021/8/20
 */
public class DownLoadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response
                .newBuilder()
                .body(new FileResponseBody(response))
                .build();
    }
}
