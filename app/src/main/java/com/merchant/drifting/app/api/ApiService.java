package com.merchant.drifting.app.api;

import com.jess.arms.base.BaseEntity;
import com.merchant.drifting.data.entity.ApplicationMaterialsEntity;
import com.merchant.drifting.mvp.model.entity.AuditingEntity;
import com.merchant.drifting.mvp.model.entity.AvailableAllEntity;
import com.merchant.drifting.mvp.model.entity.BankListEntity;
import com.merchant.drifting.mvp.model.entity.BusinessBalanceEntity;
import com.merchant.drifting.mvp.model.entity.BusinessBillEntity;
import com.merchant.drifting.mvp.model.entity.GoodsInfoEntity;
import com.merchant.drifting.mvp.model.entity.GoodsListEntity;
import com.merchant.drifting.mvp.model.entity.HaveShopEntity;
import com.merchant.drifting.mvp.model.entity.InfoEditEntity;
import com.merchant.drifting.mvp.model.entity.LoginEntity;
import com.merchant.drifting.mvp.model.entity.MessageUnreadEntity;
import com.merchant.drifting.mvp.model.entity.OrderRecordEntity;
import com.merchant.drifting.mvp.model.entity.ShopApplyLogEntity;
import com.merchant.drifting.mvp.model.entity.ShopInfoEntity;
import com.merchant.drifting.mvp.model.entity.ShopListEntity;
import com.merchant.drifting.mvp.model.entity.ShopStaticOrderEntity;
import com.merchant.drifting.mvp.model.entity.SystemNotificationEntity;
import com.merchant.drifting.mvp.model.entity.TodayOrderEntity;
import com.merchant.drifting.mvp.model.entity.VersionUpdateEntity;
import com.merchant.drifting.mvp.model.entity.WriteOffDetailEntity;
import com.merchant.drifting.mvp.model.entity.WriteOffEntity;
import com.merchant.drifting.mvp.model.entity.WriteOffListEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @Description: API接口
 * @Author: wjq
 * @CreateDate: 2022/2/15 13:26
 */
public interface ApiService {

    /**
     * app下载
     *
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

    /**
     * 版本更新
     *
     * @return
     */
    @GET("n/version/update")
    Observable<BaseEntity<VersionUpdateEntity>> checkVersion();


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
     * 编辑店铺资料
     */
    @POST("v/shop/applyForEdit")
    Observable<BaseEntity> applyForEdit(@Body MultipartBody body);


    /**
     * 申请记录
     */
    @FormUrlEncoded
    @POST("v/shop/applyLog")
    Observable<BaseEntity<List<ShopApplyLogEntity>>> shopapplyLog(@Field("status") int status);


    /**
     * 读取店铺信息（编辑时使用）
     */
    @FormUrlEncoded
    @POST("v/shop/infoForEdit")
    Observable<BaseEntity<InfoEditEntity>> infoForEdit(@Field("shop_id") String shop_id);


    /**
     * 余额、今日订单量、营业额（首页）
     */
    @FormUrlEncoded
    @POST("v/shop/statistic/today")
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
    Observable<BaseEntity<BusinessBillEntity>> businessbill(@Field("shop_id") String shop_id,@Field("search_type") int search_type, @Field("date") String date, @Field("page") int page, @Field("limit") int limit);


    /**
     * 核销
     */
    @FormUrlEncoded
    @POST("v/shop/writeOff")
    Observable<BaseEntity<WriteOffEntity>> shopwriteOff(@Field("token") String token, @Field("shop_id") String shop_id);


    /**
     * 核销过的订单
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/shop/writeOffList")
    Observable<BaseEntity<WriteOffListEntity>> writeOffList(@Field("shop_id") String shop_id, @Field("page") int page, @Field("limit") int limit);

    /**
     * 订单详情（被核销订单）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/shop/writeOffDetail")
    Observable<BaseEntity<WriteOffDetailEntity>> writeOffDetail(@Field("write_off_id") int write_off_id, @Field("shop_id") String shop_id);


    /**
     * 订单数据
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/shop/statistic/order")
    Observable<BaseEntity<ShopStaticOrderEntity>> shopstatisticorder(@Field("shop_id") String shop_id);

    /**
     * 我的余额
     */
    @FormUrlEncoded
    @POST("v/business/balance")
    Observable<BaseEntity<BusinessBalanceEntity>> balance(@Field("shop_id") String shop_id);


    /**
     * 银行卡列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/business/bank/list")
    Observable<BaseEntity<List<BankListEntity>>> banklist(@Field("shop_id") String shop_id);


    /**
     * 添加银行卡
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/business/bank/add")
    Observable<BaseEntity> bankadd(@Field("shop_id") String shop_id, @Field("name") String name, @Field("bank_name") String bank_name, @Field("branch_name") String branch_name, @Field("card_no") String card_no, @Field("mobile") String mobile, @Field("code") String code);


    /**
     * 解绑银行卡
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/business/bank/unbind")
    Observable<BaseEntity> unbind(@Field("shop_id") String shop_id,@Field("bank_card_id") String bank_card_id);


    /**
     * 全部可用杯（申请商品）
     *
     * @return
     */
    @FormUrlEncoded
    @POST("v/goods/availableAll")
    Observable<BaseEntity<List<AvailableAllEntity>>> availableAll(@Field("shop_id") String shop_id);


    /**
     * 添加商品
     */
    @POST("v/goods/add")
    Observable<BaseEntity> goodsadd(@Body MultipartBody body);


    /**
     * 审核列表
     */
    @FormUrlEncoded
    @POST("v/goods/auditing")
    Observable<BaseEntity<List<AuditingEntity>>> goodsauditing(@Field("shop_id") String shop_id, @Field("audit") int audit);

    /**
     * 商品列表（商品管理）
     */
    @FormUrlEncoded
    @POST("v/goods/list")
    Observable<BaseEntity<List<GoodsListEntity>>> goodslist(@Field("shop_id") String shop_id, @Field("selling") int selling);


    /**
     * 商品上下架
     */
    @FormUrlEncoded
    @POST("v/goods/shelf")
    Observable<BaseEntity> goodsshelf(@Field("shop_id") String shop_id, @Field("selling") int selling, @Field("sku_codes") String sku_codes);


    /**
     * 商品信息
     */
    @FormUrlEncoded
    @POST("v/goods/info")
    Observable<BaseEntity<GoodsInfoEntity>> goodsinfo(@Field("shop_id") String shop_id, @Field("sku_code") String  sku_code);

    /**
     * 编辑商品
     */
    @POST("v/goods/edit")
    Observable<BaseEntity> goodsedit(@Body MultipartBody body);


    /**
     * 店铺信息
     */
    @FormUrlEncoded
    @POST("v/shop/info")
    Observable<BaseEntity<ShopInfoEntity>> shopinfo(@Field("shop_id") String shop_id);

    /**
     * 更新营业时间
     */
    @FormUrlEncoded
    @POST("v/shop/setOpeningTime")
    Observable<BaseEntity> setOpeningTime(@Field("shop_id") String shop_id,@Field("opening") String opening,@Field("opening_end") String opening_end);


    /**
     * 全部标记已读
     */
    @GET("v/business/unregister")
    Observable<BaseEntity> businessunregister();


}
