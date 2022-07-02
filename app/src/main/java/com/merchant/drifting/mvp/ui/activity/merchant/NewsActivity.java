package com.merchant.drifting.mvp.ui.activity.merchant;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.data.event.MessageReadEvent;
import com.merchant.drifting.di.component.DaggerNewsComponent;
import com.merchant.drifting.mvp.contract.NewsContract;
import com.merchant.drifting.mvp.presenter.NewsPresenter;
import com.merchant.drifting.mvp.ui.activity.index.ApplicationRecordActivity;
import com.merchant.drifting.mvp.ui.activity.index.SystemNotificationActivity;
import com.merchant.drifting.mvp.ui.dialog.MessageDialog;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.DateUtil;
import com.merchant.drifting.util.callback.BaseDataCallBack;
import com.merchant.drifting.util.request.RequestUtil;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/20 13:59
 *
 * @author 消息
 * module name is NewsActivity
 */
public class NewsActivity extends BaseActivity<NewsPresenter> implements NewsContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.iv_system_unread)
    ImageView mIvSystemUnread;
    @BindView(R.id.iv_order_unread)
    ImageView mIvOrderUnread;
    @BindView(R.id.tv_system_desc)
    TextView mTvSystemDsec;
    @BindView(R.id.tv_order_desc)
    TextView mTvOrderDsec;
    @BindView(R.id.tv_system_time)
    TextView mTvSystemTime;
    @BindView(R.id.tv_order_time)
    TextView mTvOrderTime;
    private MessageDialog messageDialog;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, NewsActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNewsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_news; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);

        mToolbarTitle.setText("消息");
        initListener();
    }

    public void initListener() {
        RequestUtil.create().messageunread(entity -> {
            if (entity.getCode() == 200) {
                mIvSystemUnread.setVisibility(entity.getData().getSys_msg() != 0 ? View.VISIBLE : View.GONE);
                mIvOrderUnread.setVisibility(entity.getData().getOrder_msg() != 0 ? View.VISIBLE : View.GONE);
                if (!TextUtils.isEmpty(entity.getData().getSys_title())) {
                    mTvSystemDsec.setText(entity.getData().getSys_title());
                }
                if (!TextUtils.isEmpty(entity.getData().getOrder_title())) {
                    mTvOrderDsec.setText(entity.getData().getOrder_title());
                }
                if (entity.getData().getSys_created_at() != 0 || entity.getData().getOrder_created_at() != 0) {
                    mIvRight.setImageResource(R.drawable.edit_message);
                    mIvRight.setVisibility(View.VISIBLE);
                    if (entity.getData().getSys_created_at() != 0) {
                        mTvSystemTime.setText(DateUtil.getShortTime(entity.getData().getSys_created_at() + ""));
                    }
                    if (entity.getData().getOrder_created_at() != 0) {
                        mTvOrderTime.setText(DateUtil.getShortTime(entity.getData().getOrder_created_at() + ""));
                    }
                }
            }
        });
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @OnClick({R.id.toolbar_back, R.id.iv_right, R.id.rl_system, R.id.rl_order})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_right:
                    messageDialog = new MessageDialog(this);
                    messageDialog.show();
                    messageDialog.setOnClickCallback(type -> {
                        if (type == MessageDialog.SELECT_FINISH) {
                            RequestUtil.create().markread(entity -> {
                                if (entity != null && entity.getCode() == 200) {
                                    mIvSystemUnread.setVisibility(View.GONE);
                                    mIvOrderUnread.setVisibility(View.GONE);
                                    EventBus.getDefault().post(new MessageReadEvent());
                                }
                            });
                        }
                    });
                    break;
                case R.id.rl_system:
                    SystemNotificationActivity.start(this, 1, false);
                    break;
                case R.id.rl_order:
                    SystemNotificationActivity.start(this, 2, false);


                    break;
            }
        }
    }
}