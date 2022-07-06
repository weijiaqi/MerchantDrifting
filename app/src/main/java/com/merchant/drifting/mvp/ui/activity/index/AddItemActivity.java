package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.di.component.AppComponent;

import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.engine.CropFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.style.BottomNavBarStyle;
import com.luck.picture.lib.style.PictureSelectorStyle;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.style.TitleBarStyle;
import com.luck.picture.lib.utils.DateUtils;
import com.luck.picture.lib.utils.StyleUtils;
import com.merchant.drifting.R;
import com.merchant.drifting.data.event.AddItemEvent;
import com.merchant.drifting.di.component.DaggerAddItemComponent;
import com.merchant.drifting.mvp.contract.AddItemContract;
import com.merchant.drifting.mvp.model.entity.GoodsInfoEntity;
import com.merchant.drifting.mvp.presenter.AddItemPresenter;

import com.merchant.drifting.mvp.ui.dialog.NewLabelDialog;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.GlideEngine;
import com.merchant.drifting.util.GlideUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.ToastUtil;
import com.merchant.drifting.util.ViewUtil;

import com.merchant.drifting.view.flowlayout.FlowLayout;
import com.merchant.drifting.view.flowlayout.TagAdapter;
import com.merchant.drifting.view.flowlayout.TagFlowLayout;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropImageEngine;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;


/**
 * Created on 2022/07/05 10:46
 *
 * @author 添加商品
 * module name is AddItemActivity
 */
public class AddItemActivity extends BaseActivity<AddItemPresenter> implements AddItemContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.scrap_layout)
    TagFlowLayout mScrapLayout;
    @BindView(R.id.temperature_layout)
    TagFlowLayout mTemperatureLayout;
    @BindView(R.id.sweetness_layout)
    TagFlowLayout mSweetnessLayout;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_introduce)
    EditText mEtIntroduce;
    @BindView(R.id.et_weight)
    EditText mEtWeight;
    @BindView(R.id.et_offer)
    EditText mEtOffer;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_add)
    TextView mTvAdd;
    @BindView(R.id.tv_cofim_the_application)
    TextView mTvCofimApplication;
    private int type, status;
    private NewLabelDialog newLabelDialog;
    private List<String> scraplist = new ArrayList<>();
    private List<String> temperaturelist = new ArrayList<>();
    private List<String> sweetnessist = new ArrayList<>();

    private PictureSelectorStyle selectorStyle;
    private static final int SHOPPIC = 1;
    private String shoppic, explore_id, goodscode;

    private static String EXTRA_STATUS = "extra_status";
    private static String EXTRA_EXPLORE_ID = "extra_explore_id";
    private static String EXTRA_GOODS_CODE = "extra_goods_code";

    public static void start(Context context, int status, String explore_id, String goodscode, boolean closePage) {
        Intent intent = new Intent(context, AddItemActivity.class);
        intent.putExtra(EXTRA_STATUS, status);
        intent.putExtra(EXTRA_EXPLORE_ID, explore_id);
        intent.putExtra(EXTRA_GOODS_CODE, goodscode);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddItemComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_item; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        if (getIntent() != null) {
            status = getIntent().getExtras().getInt(EXTRA_STATUS);
            explore_id = getIntent().getExtras().getString(EXTRA_EXPLORE_ID);
            goodscode = getIntent().getExtras().getString(EXTRA_GOODS_CODE);
        }
        mToolbarTitle.setText(status == 1 ? "添加商品" : "查看商品");
        initListener();
        if (status != 1) {
            if (mPresenter != null) {
                mPresenter.goodsinfo(Preferences.getShopId(), goodscode);
            }
        }
    }

    public void initListener() {
        setPicStyle();
        mScrapLayout.setOnTagClickListener((view, position, parent) -> {
            if (scraplist.size() > 0) {
                scraplist.remove(position);
                mScrapLayout.onChanged();
            }
            return true;
        });
        mTemperatureLayout.setOnTagClickListener((view, position, parent) -> {
            if (temperaturelist.size() > 0) {
                temperaturelist.remove(position);
                mTemperatureLayout.onChanged();
            }
            return true;
        });
        mSweetnessLayout.setOnTagClickListener((view, position, parent) -> {
            if (sweetnessist.size() > 0) {
                sweetnessist.remove(position);
                mSweetnessLayout.onChanged();
            }
            return true;
        });
    }

    @Override
    public void OnGoodsAddSuccess() {
        showMessage("提交成功");
        finish();
    }

    @Override
    public void OnGoodsInfoSuccess(GoodsInfoEntity entity) {
        if (entity != null) {
            if (entity.getAudit() == 1 || entity.getAudit() == 2) {
                mTvCofimApplication.setVisibility(View.GONE);
                mEtName.setEnabled(false);
                mEtIntroduce.setEnabled(false);
                mEtWeight.setEnabled(false);
                mEtOffer.setEnabled(false);
                mIvPic.setClickable(false);
            } else if (entity.getAudit() == 3) {  //驳回
                mToolbarTitle.setText("编辑商品");
            }
            shoppic = entity.getGoods_image();
            GlideUtil.create().loadLongImage(this, entity.getGoods_image(), mIvPic);
            mEtName.setText(entity.getName());
            mEtIntroduce.setText(entity.getIntro());
            mEtWeight.setText(entity.getSpecs());
            mEtOffer.setText(entity.getPrice());
            List result = Arrays.asList(entity.getIngredient().split(","));
            scraplist.addAll(result);
            List result2 = Arrays.asList(entity.getTemperature().split(","));
            temperaturelist.addAll(result2);
            List result3 = Arrays.asList(entity.getSweet().split(","));
            sweetnessist.addAll(result3);
            AddScrapLayout();
            AddTemperatureLayout();
            AddSweetnessLayout();
        }
    }

    @Override
    public void OnGoodsEditSuccess() {
        showMessage("提交成功");

        AddItemEvent addItemEvent=new AddItemEvent();
        addItemEvent.setSku_code(goodscode);
        EventBus.getDefault().post(addItemEvent);
        finish();

    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }


    //小料
    public void AddScrapLayout() {
        mScrapLayout.setAdapter(new TagAdapter<String>(scraplist) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(AddItemActivity.this).inflate(R.layout.layout_for_scrap,
                        mScrapLayout, false);
                tv.setText(s);
                return tv;
            }
        });

    }

    //温度
    public void AddTemperatureLayout() {
        mTemperatureLayout.setAdapter(new TagAdapter<String>(temperaturelist) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(AddItemActivity.this).inflate(R.layout.layout_for_scrap,
                        mTemperatureLayout, false);
                tv.setText(s);
                return tv;
            }
        });

    }

    //甜度
    public void AddSweetnessLayout() {
        mSweetnessLayout.setAdapter(new TagAdapter<String>(sweetnessist) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(AddItemActivity.this).inflate(R.layout.layout_for_scrap,
                        mSweetnessLayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }


    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @OnClick({R.id.toolbar_back, R.id.add_scrap, R.id.tv_temperature, R.id.tv_sweetness, R.id.iv_pic, R.id.tv_cofim_the_application})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_pic:
                    goToSelectPic(SHOPPIC);
                    break;
                case R.id.add_scrap:   //添加甜度
                    type = 1;
                    setType(type);
                    break;
                case R.id.tv_temperature:  //添加温度
                    type = 2;
                    setType(type);
                    break;
                case R.id.tv_sweetness:  //添加甜度
                    type = 3;
                    setType(type);
                    break;
                case R.id.tv_cofim_the_application:  //申请

                    if (StringUtil.isEmpty(shoppic)) {
                        showMessage("请添加商品图片");
                        return;
                    }

                    if (StringUtil.isEmpty(mEtName.getText().toString())) {
                        showMessage("请输入名称");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtIntroduce.getText().toString())) {
                        showMessage("请输入介绍");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtWeight.getText().toString())) {
                        showMessage("请输入分量");
                        return;
                    }

                    if (scraplist.size() == 0) {
                        showMessage("请添加小料标签");
                        return;
                    }
                    if (temperaturelist.size() == 0) {
                        showMessage("请添加温度标签");
                        return;
                    }
                    if (sweetnessist.size() == 0) {
                        showMessage("请添加甜度标签");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtOffer.getText().toString())) {
                        showMessage("请输入报价");
                        return;
                    }
                    showLoading();
                    if (mPresenter != null) {
                        if (type == 1) {
                            mPresenter.applyForEdit(explore_id, goodscode, mEtIntroduce.getText().toString(), mEtName.getText().toString(), mEtWeight.getText().toString(), mEtOffer.getText().toString(), Preferences.getShopId(), StringUtil.addlist(sweetnessist), StringUtil.addlist(temperaturelist), StringUtil.addlist(scraplist), new File(shoppic));
                        } else {
                            mPresenter.goodsedit(goodscode, mEtIntroduce.getText().toString(), mEtName.getText().toString(), mEtWeight.getText().toString(), mEtOffer.getText().toString(), Preferences.getShopId(), StringUtil.addlist(sweetnessist), StringUtil.addlist(temperaturelist), StringUtil.addlist(scraplist), shoppic);
                        }
                    }

                    break;
            }
        }
    }

    @Override
    public void showLoading() {
        ViewUtil.create().show(this);
    }

    @Override
    public void hideLoading() {
        ViewUtil.create().dismiss();
    }


    private void goToSelectPic(int type) {
        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setSelectorUIStyle(selectorStyle)
                .setImageEngine(GlideEngine.createGlideEngine())
                .setCropEngine(getCropFileEngine())
                .isDirectReturnSingle(true)
                .setMinSelectNum(1)
                .setMaxSelectNum(1)
                .isMaxSelectEnabledMask(true)
                .isGif(false)
                .setCompressEngine(getCompressFileEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        if (type == SHOPPIC) {
                            mTvAdd.setVisibility(View.GONE);
                            shoppic = result.get(0).getCompressPath();
                            GlideUtil.create().loadLongImage(AddItemActivity.this, shoppic, mIvPic);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }

    /**
     * 裁剪引擎
     *
     * @return
     */
    private ImageFileCropEngine getCropFileEngine() {
        return new ImageFileCropEngine();
    }

    /**
     * 自定义裁剪
     */
    private class ImageFileCropEngine implements CropFileEngine {

        @Override
        public void onStartCrop(Fragment fragment, Uri srcUri, Uri destinationUri, ArrayList<String> dataSource, int requestCode) {
            UCrop.Options options = buildOptions();
            UCrop uCrop = UCrop.of(srcUri, destinationUri, dataSource);
            uCrop.withOptions(options);
            uCrop.setImageEngine(new UCropImageEngine() {
                @Override
                public void loadImage(Context context, String url, ImageView imageView) {
                    if (!GlideUtil.assertValidRequest(context)) {
                        return;
                    }
                    Glide.with(context).load(url).override(180, 180).into(imageView);
                }

                @Override
                public void loadImage(Context context, Uri url, int maxWidth, int maxHeight, OnCallbackListener<Bitmap> call) {
                    if (!GlideUtil.assertValidRequest(context)) {
                        return;
                    }
                    Glide.with(context).asBitmap().override(maxWidth, maxHeight).load(url).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            if (call != null) {
                                call.onCall(resource);
                            }
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            if (call != null) {
                                call.onCall(null);
                            }
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
                }
            });
            uCrop.start(fragment.getActivity(), fragment, requestCode);
        }
    }


    /**
     * 配制UCrop，可根据需求自我扩展
     *
     * @return
     */
    private UCrop.Options buildOptions() {
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(true);
        options.setShowCropFrame(true);
        options.setShowCropGrid(true);
        options.setCircleDimmedLayer(false);
        options.withAspectRatio(1, 1);
        options.setCropOutputPathDir(getSandboxPath());
        options.isCropDragSmoothToCenter(false);
        options.isUseCustomLoaderBitmap(true);
        options.isForbidCropGifWebp(true);
        options.isForbidSkipMultipleCrop(false);
        options.setMaxScaleMultiplier(100);
        if (selectorStyle != null && selectorStyle.getSelectMainStyle().getStatusBarColor() != 0) {
            SelectMainStyle mainStyle = selectorStyle.getSelectMainStyle();
            boolean isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack();
            int statusBarColor = mainStyle.getStatusBarColor();
            options.isDarkStatusBarBlack(isDarkStatusBarBlack);
            if (StyleUtils.checkStyleValidity(statusBarColor)) {
                options.setStatusBarColor(statusBarColor);
                options.setToolbarColor(statusBarColor);
            } else {
                options.setStatusBarColor(ContextCompat.getColor(this, R.color.ps_color_grey));
                options.setToolbarColor(ContextCompat.getColor(this, R.color.ps_color_grey));
            }
            TitleBarStyle titleBarStyle = selectorStyle.getTitleBarStyle();
            if (StyleUtils.checkStyleValidity(titleBarStyle.getTitleTextColor())) {
                options.setToolbarWidgetColor(titleBarStyle.getTitleTextColor());
            } else {
                options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.ps_color_white));
            }
        } else {
            options.setStatusBarColor(ContextCompat.getColor(this, R.color.ps_color_grey));
            options.setToolbarColor(ContextCompat.getColor(this, R.color.ps_color_grey));
            options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.ps_color_white));
        }
        return options;
    }

    /**
     * 创建自定义输出目录
     *
     * @return
     */
    private String getSandboxPath() {
        File externalFilesDir = getExternalFilesDir("");
        File customFile = new File(externalFilesDir.getAbsolutePath(), "Sandbox");
        if (!customFile.exists()) {
            customFile.mkdirs();
        }
        return customFile.getAbsolutePath() + File.separator;
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

    public void setPicStyle() {
        selectorStyle = new PictureSelectorStyle();
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

    public void setType(int type) {
        newLabelDialog = new NewLabelDialog(this);
        newLabelDialog.show();
        newLabelDialog.setOnContentClickCallback(content -> {
            mEtName.clearFocus();
            mEtIntroduce.clearFocus();
            mEtOffer.clearFocus();
            mEtWeight.clearFocus();
            if (type == 1) {
                scraplist.add(content);
                AddScrapLayout();
            } else if (type == 2) {
                temperaturelist.add(content);
                AddTemperatureLayout();
            } else {
                sweetnessist.add(content);
                AddSweetnessLayout();
            }
        });
    }


}