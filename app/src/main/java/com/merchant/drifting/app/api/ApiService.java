package com.merchant.drifting.app.api;

import com.jess.arms.base.BaseEntity;
import com.merchant.drifting.data.entity.ApplicationMaterialsEntity;
import com.merchant.drifting.mvp.model.entity.BusinessBillEntity;
import com.merchant.drifting.mvp.model.entity.HaveShopEntity;
import com.merchant.drifting.mvp.model.entity.LoginEntity;
import com.merchant.drifting.mvp.model.entity.MessageUnreadEntity;
import com.merchant.drifting.mvp.model.entity.OrderRecordEntity;
import com.merchant.drifting.mvp.model.entity.ShopApplyLogEntity;
import com.merchant.drifting.mvp.model.entity.ShopListEntity;
import com.merchant.drifting.mvp.model.entity.SystemNotificationEntity;
import com.merchant.drifting.mvp.model.entity.TodayOrderEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
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
    Observable<BaseEntity> verificationcode(@Field("mobile") String mobile, @Field("status") int status);


    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("n/business/register")
    Observable<BaseEntity<LoginEntity>> register(@Field("mobile") String mobile, @Field("code") String code);


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


    /**
     * 提交资料
     */
    @POST("v/shop/apply")
    Observable<BaseEntity<ApplicationMaterialsEntity>> shopapply(@Body MultipartBody body);


    /**
     * 申请记录
     */
    @FormUrlEncoded
    @POST("v/shop/applyLog")
    Observable<BaseEntity<List<ShopApplyLogEntity>>> shopapplyLog(@Field("status") int status);


    /**
     * 余额、今日订单量、营业额（首页）
     */
    @FormUrlEncoded
    @POST("v/shop/applyLog")
    Observable<BaseEntity<TodayOrderEntity>> statistictoday(@Field("shop_id") String shop_id);


    /**
     * 销售排行榜（首页）
     */
    @FormUrlEncoded
    @POST("v/shop/sales/ranking")
    Observable<BaseEntity<List<OrderRecordEntity>>> salesranking(@Field("shop_id") String shop_id, @Field("days") int days);


    /**
     * 未读消息
     */
    @GET("v/message/unread")
    Observable<BaseEntity<MessageUnreadEntity>> messageunread();


    /**
     * 全部标记已读
     */
    @GET("v/message/markread")
    Observable<BaseEntity> markread();


    /**
     * 通知列表（系统、订单）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/message/list")
    Observable<BaseEntity<SystemNotificationEntity>> messagelist(@Field("msg_type") int msg_type, @Field("page") int page, @Field("limit") int limit);


    /**
     * 流水记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/business/bill")
    Observable<BaseEntity<BusinessBillEntity>> businessbill(@Field("search_type") int search_type, @Field("date") String date, @Field("page") int page, @Field("limit") int limit);


}
