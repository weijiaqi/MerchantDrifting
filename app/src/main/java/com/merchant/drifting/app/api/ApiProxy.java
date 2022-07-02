package com.merchant.drifting.app.api;



import com.merchant.drifting.app.interceptor.CommonParInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @Description: 代理
 * @Author: wjq
 * @CreateDate: 2021/9/10 18:25
 */
public class ApiProxy {
    private static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(20, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(new CommonParInterceptor())
                    .build();
            Retrofit sRetrofit = new Retrofit.Builder()
                    .baseUrl(Api.API_SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            apiService = sRetrofit.create(ApiService.class);
        }
        return apiService;
    }
}
