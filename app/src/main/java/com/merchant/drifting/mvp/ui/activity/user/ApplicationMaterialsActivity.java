package com.merchant.drifting.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;


import com.luck.picture.lib.basic.PictureSelectionCameraModel;
import com.luck.picture.lib.basic.PictureSelectionModel;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.style.BottomNavBarStyle;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.DateUtils;
import com.merchant.drifting.R;
import com.merchant.drifting.app.FilePathConstant;
import com.merchant.drifting.di.component.DaggerApplicationMaterialsComponent;
import com.merchant.drifting.mvp.contract.ApplicationMaterialsContract;
import com.merchant.drifting.mvp.presenter.ApplicationMaterialsPresenter;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.GlideEngine;
import com.merchant.drifting.util.GlideUtil;
import com.merchant.drifting.util.SpannableUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;


/**
 * Created on 2022/06/23 13:21
 *
 * @author 提交资料
 * module name is ApplicationMaterialsActivity
 */
public class ApplicationMaterialsActivity extends BaseActivity<ApplicationMaterialsPresenter> implements ApplicationMaterialsContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_facade)
    TextView mTvFacede;
    @BindView(R.id.tv_positive)
    TextView mTvPositive;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_license_positive)
    TextView mTvlicensePositive;
    @BindView(R.id.tv_licence_positive)
    TextView mTvlicencePositive;
    @BindView(R.id.iv_facade)
    ImageView mIvFacade;
    @BindView(R.id.iv_environment)
    ImageView mIvEnvironment;
    private SpannableStringBuilder passer1, passer2, passer3, passer4;
    private static final int FACADE = 1, ENVIRONMENT = 2, IDCARD_BACKGROUND = 3, CERTIFICATE = 4, COACH_CERT = 5;
    private String facade, environment;
    private PictureSelectorStyle selectorStyle;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ApplicationMaterialsActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerApplicationMaterialsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_application_materials; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("提交资料");
        initListener();
    }

    public void initListener() {
        selectorStyle = new PictureSelectorStyle();
        passer1 = SpannableUtil.getBuilder(this, "门面图 ").append("（需要包含完整牌匾）").setForegroundColor(R.color.color_42).build();
        mTvFacede.setText(passer1);
        passer2 = SpannableUtil.getBuilder(this, "正面 ").append("（*头像页）").setForegroundColor(R.color.color_c2).build();
        mTvPositive.setText(passer2);
        passer3 = SpannableUtil.getBuilder(this, "背面 ").append("（*国徽页）").setForegroundColor(R.color.color_c2).build();
        mTvBack.setText(passer3);
        passer4 = SpannableUtil.getBuilder(this, "证件正面照 ").append("（*注意照片清晰度）").setForegroundColor(R.color.color_c2).build();
        mTvlicensePositive.setText(passer4);
        mTvlicencePositive.setText(passer4);

        BottomNavBarStyle whiteBottomNavBarStyle = new BottomNavBarStyle();
        whiteBottomNavBarStyle.setBottomPreviewNormalTextColor(ContextCompat.getColor(this, R.color.ps_color_9b));
        whiteBottomNavBarStyle.setBottomPreviewSelectTextColor(ContextCompat.getColor(this, R.color.ps_color_blue));
        whiteBottomNavBarStyle.setBottomSelectNumResources(R.drawable.shape_blue_circle);
        whiteBottomNavBarStyle.setBottomEditorTextColor(ContextCompat.getColor(this, R.color.ps_color_53575e));
        whiteBottomNavBarStyle.setBottomOriginalTextColor(ContextCompat.getColor(this, R.color.ps_color_53575e));
        SelectMainStyle selectMainStyle = new SelectMainStyle();
        selectMainStyle.setSelectTextColor(ContextCompat.getColor(this, R.color.ps_color_blue));
        selectorStyle.setSelectMainStyle(selectMainStyle);
        selectorStyle.setBottomBarStyle(whiteBottomNavBarStyle);

    }

    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.toolbar_back, R.id.iv_facade, R.id.iv_environment,R.id.tv_submit})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_facade:  //门面图
                    goToSelectPic(FACADE);
                    break;
                case R.id.iv_environment:  //环境图
                    goToSelectPic(ENVIRONMENT);
                    break;
                case R.id.tv_submit:  //提交审核
                    ApplicationCompletedActivity.start(this,false);
                    break;
            }
        }
    }

    private void goToSelectPic(int type) {
        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setSelectorUIStyle(selectorStyle)
                .setImageEngine(GlideEngine.createGlideEngine())
                .isDirectReturnSingle(true)
                .setMinSelectNum(1)
                .setMaxSelectNum(1)
                .isMaxSelectEnabledMask(true)
                .isGif(false)
                .setCompressEngine(getCompressFileEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        if (type == FACADE) {
                            facade = result.get(0).getCompressPath();
                            GlideUtil.create().loadLongImage(ApplicationMaterialsActivity.this, facade, mIvFacade);
                        } else if (type == ENVIRONMENT) {
                            environment = result.get(0).getCompressPath();
                            GlideUtil.create().loadLongImage(ApplicationMaterialsActivity.this, environment, mIvEnvironment);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }


    /**
     * 压缩引擎
     *
     * @return
     */
    private ImageFileCompressEngine getCompressFileEngine() {
        return new ImageFileCompressEngine();
    }


    /**
     * 自定义压缩
     */
    private static class ImageFileCompressEngine implements CompressFileEngine {

        @Override
        public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
            Luban.with(context).load(source).ignoreBy(100).setRenameListener(new OnRenameListener() {
                @Override
                public String rename(String filePath) {
                    int indexOf = filePath.lastIndexOf(".");
                    String postfix = indexOf != -1 ? filePath.substring(indexOf) : ".jpg";
                    return DateUtils.getCreateFileName("CMP_") + postfix;
                }
            }).setCompressListener(new OnNewCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(String source, File compressFile) {
                    if (call != null) {
                        call.onCallback(source, compressFile.getAbsolutePath());
                    }
                }

                @Override
                public void onError(String source, Throwable e) {
                    if (call != null) {
                        call.onCallback(source, null);
                    }
                }
            }).launch();
        }
    }


    @Override
    public void showMessage(@NonNull String message) {

    }
}