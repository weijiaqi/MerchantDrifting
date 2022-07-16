package com.merchant.drifting.app.api;



import com.merchant.drifting.app.interceptor.CommonParInterceptor;
import com.merchant.drifting.app.interceptor.DownLoadInterceptor;

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
    private static ApiService apiDownload;
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


    public static ApiService getDownLoad() {
        if (apiDownload == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .addNetworkInterceptor(new DownLoadInterceptor())
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Api.API_SERVER)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiDownload = retrofit.create(ApiService.class);
        }
        return apiDownload;
    }
}
