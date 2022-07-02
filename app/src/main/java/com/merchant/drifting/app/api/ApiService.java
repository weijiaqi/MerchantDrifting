package com.merchant.drifting.app.api;

import com.jess.arms.base.BaseEntity;
import com.merchant.drifting.mvp.model.entity.HaveShopEntity;
import com.merchant.drifting.mvp.model.entity.LoginEntity;
import com.merchant.drifting.mvp.model.entity.ShopListEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @Description: API接口
 * @Author: wjq
 * @CreateDate: 2022/2/15 13:26
 */
public interface ApiService {

    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("n/send/verificationcode")
    Observable<BaseEntity> verificationcode(@Field("mobile") String mobile,@Field("status") int status);



    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("n/business/register")
    Observable<BaseEntity<LoginEntity>> register(@Field("mobile") String mobile,@Field("code") String code);


    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("n/business/login")
    Observable<BaseEntity<LoginEntity>> login(@Field("mobile") String mobile, @Field("code") String code);


    /**
     * 判断是否有店铺
     */

    @GET("v/business/haveshop")
    Observable<BaseEntity<HaveShopEntity>> haveshop();


    /**
     * 店铺列表（商家中心）
     */

    @GET("v/shop/list")
    Observable<BaseEntity<List<ShopListEntity>>> shoplist();



}
