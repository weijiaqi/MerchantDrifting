package com.merchant.drifting.mvp.ui.activity.user;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.PermissionUtils;
import com.baidu.mapapi.model.LatLng;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.di.component.AppComponent;


import com.jess.arms.utils.PermissionUtil;
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
import com.merchant.drifting.data.entity.ApplicationMaterialsEntity;
import com.merchant.drifting.di.component.DaggerApplicationMaterialsComponent;
import com.merchant.drifting.location.SelectAddressByMapActivity;
import com.merchant.drifting.mvp.contract.ApplicationMaterialsContract;
import com.merchant.drifting.mvp.model.entity.InfoEditEntity;
import com.merchant.drifting.mvp.presenter.ApplicationMaterialsPresenter;
import com.merchant.drifting.mvp.ui.activity.home.HomeActivity;
import com.merchant.drifting.mvp.ui.dialog.DocumentTypeDialog;
import com.merchant.drifting.mvp.ui.dialog.SelectSexDialog;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.GlideEngine;
import com.merchant.drifting.util.GlideUtil;
import com.merchant.drifting.util.MapUtil;
import com.merchant.drifting.util.PermissionDialog;
import com.merchant.drifting.util.SpannableUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.ToastUtil;
import com.merchant.drifting.util.ViewUtil;
import com.merchant.drifting.view.ClearEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;


/**
 * Created on 2022/06/23 13:21
 *
 * @author ????????????
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
    @BindView(R.id.iv_location)
    ImageView mIvLocation;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.tv_document_type)
    TextView mTvDocumentType;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_store_name)
    EditText mEtStoreName;
    @BindView(R.id.et_legal_name)
    EditText mEtLegalName;
    @BindView(R.id.et_certificate_no)
    EditText mEtCertificateNo;
    @BindView(R.id.iv_id_photo)
    ImageView mIvIdPhoto;
    @BindView(R.id.iv_id_photo_back)
    ImageView mIvIdPhotoBack;
    @BindView(R.id.iv_business_license)
    ImageView mIvBusinessLicense;
    @BindView(R.id.iv_licence)
    ImageView mIvLicence;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.ll_location)
    LinearLayout mLlLocation;
    private String address;
    private SpannableStringBuilder passer1, passer2, passer3, passer4;
    private static final int FACADE = 1, ENVIRONMENT = 2, IDCARD = 3, IDCARD_BACK = 4, BUSINESS_LICENSE = 5, LICENSE = 6;
    private int mSex = -1, document_type = 0;
    private String facade, environment, IdPhoto, IdPhotoBack, BusinessLicense, Licence, lng, lat, province;
    private PictureSelectorStyle selectorStyle;
    private SelectSexDialog selectSexDialog;
    private DocumentTypeDialog documentTypeDialog;
    private static String EXTRA_TYPE = "extra_type";
    private static String EXTRA_SHOP_ID = "extra_shop_id";
    private int type;
    private String shop_id;

    public static void start(Context context, int type, String shop_id, boolean closePage) {
        Intent intent = new Intent(context, ApplicationMaterialsActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_SHOP_ID, shop_id);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerApplicationMaterialsComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_application_materials; //???????????????????????????????????? setContentView(id) ??????????????????,????????? 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        if (getIntent() != null) {
            shop_id = getIntent().getExtras().getString(EXTRA_SHOP_ID);
            type = getIntent().getExtras().getInt(EXTRA_TYPE);
        }
        mToolbarTitle.setText(type == 1 ? "????????????" : "????????????");
        initListener();
        if (type != 1) {
            if (mPresenter != null) {
                mPresenter.infoForEdit(shop_id);
            }
        }
    }

    public void initListener() {
        selectorStyle = new PictureSelectorStyle();
        passer1 = SpannableUtil.getBuilder(this, "????????? ").append("??????????????????????????????").setForegroundColor(R.color.color_42).build();
        mTvFacede.setText(passer1);
        passer2 = SpannableUtil.getBuilder(this, "?????? ").append("???*????????????").setForegroundColor(R.color.color_c2).build();
        mTvPositive.setText(passer2);
        passer3 = SpannableUtil.getBuilder(this, "?????? ").append("???*????????????").setForegroundColor(R.color.color_c2).build();
        mTvBack.setText(passer3);
        passer4 = SpannableUtil.getBuilder(this, "??????????????? ").append("???*????????????????????????").setForegroundColor(R.color.color_c2).build();
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

    @OnClick({R.id.toolbar_back, R.id.iv_facade, R.id.iv_environment, R.id.iv_id_photo, R.id.iv_id_photo_back, R.id.iv_business_license, R.id.iv_licence, R.id.ll_location, R.id.tv_sex, R.id.tv_document_type, R.id.tv_submit})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_facade:  //?????????
                    goToSelectPic(FACADE);
                    break;
                case R.id.iv_environment:  //?????????
                    goToSelectPic(ENVIRONMENT);
                    break;
                case R.id.iv_id_photo:  //???????????????
                    goToSelectPic(IDCARD);
                    break;
                case R.id.iv_id_photo_back://???????????????
                    goToSelectPic(IDCARD_BACK);
                    break;
                case R.id.iv_business_license://????????????
                    goToSelectPic(BUSINESS_LICENSE);
                    break;
                case R.id.iv_licence://?????????
                    goToSelectPic(LICENSE);
                    break;
                case R.id.ll_location:
                    // ??????????????????
                    requestPermission();
                    break;
                case R.id.tv_sex: //?????????
                    selectSexDialog = new SelectSexDialog(this);
                    selectSexDialog.show();
                    selectSexDialog.setOnClickCallback(type -> {
                        switch (type) {
                            case 0://???
                                mTvSex.setText("???");
                                break;
                            case 1://???
                                mTvSex.setText("???");
                                break;
                        }
                        mSex = type;
                    });

                    break;
                case R.id.tv_document_type:  //????????????
                    documentTypeDialog = new DocumentTypeDialog(this);
                    documentTypeDialog.show();
                    documentTypeDialog.setOnClickCallback(type -> {
                        switch (type) {
                            case 0://?????????
                                mTvDocumentType.setText("?????????");
                                break;
                        }
                        document_type = type + 1;
                    });
                    break;
                case R.id.tv_submit:
                    if (StringUtil.isEmpty(mEtName.getText().toString())) {
                        showMessage("???????????????");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtPhone.getText().toString())) {
                        showMessage("??????????????????");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtStoreName.getText().toString())) {
                        showMessage("???????????????");
                        return;
                    }

                    if (StringUtil.isEmpty(mTvAddress.getText().toString())) {
                        showMessage("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(facade)) {
                        showMessage("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(environment)) {
                        showMessage("???????????????????????????");
                        return;
                    }

                    if (StringUtil.isEmpty(mEtLegalName.getText().toString())) {
                        showMessage("?????????????????????");
                        return;
                    }
                    if (mSex == -1) {
                        showMessage("???????????????");
                        return;
                    }
                    if (document_type == 0) {
                        showMessage("?????????????????????");
                        return;
                    }

                    if (StringUtil.isEmpty(mEtCertificateNo.getText().toString())) {
                        showMessage("??????????????????");
                        return;
                    }

                    if (TextUtils.isEmpty(IdPhoto)) {
                        showMessage("????????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(IdPhotoBack)) {
                        showMessage("????????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(BusinessLicense)) {
                        showMessage("?????????????????????");
                        return;
                    }
                    if (TextUtils.isEmpty(Licence)) {
                        showMessage("??????????????????");
                        return;
                    }
                    showLoading();
                    if (mPresenter != null) {
                        if (type == 1) {   //???????????????
                            mPresenter.shopapply(mEtStoreName.getText().toString(), mEtPhone.getText().toString(), mEtName.getText().toString(), province, mTvAddress.getText().toString(), new File(facade), new File(environment), mEtLegalName.getText().toString(), mSex, document_type, mEtCertificateNo.getText().toString(), new File(IdPhoto), new File(IdPhotoBack), new File(BusinessLicense), new File(Licence), lng, lat);
                        } else {
                            mPresenter.applyForEdit(shop_id, mEtStoreName.getText().toString(), mEtPhone.getText().toString(), mEtName.getText().toString(), province, mTvAddress.getText().toString(), facade, environment, mEtLegalName.getText().toString(), mSex, document_type, mEtCertificateNo.getText().toString(), IdPhoto, IdPhotoBack, BusinessLicense, Licence, lng, lat);
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


    @Override
    public void OnShopApplySuccess(ApplicationMaterialsEntity entity) {
        StartActivity();
    }

    @Override
    public void OnInfoEditSuccess(InfoEditEntity entity) {
        if (entity != null) {
            if (entity.getStatus() == 0 || entity.getStatus() == 1) {  //?????????
                mTvSubmit.setVisibility(View.GONE);
                mEtName.setEnabled(false);
                mEtPhone.setEnabled(false);
                mEtStoreName.setEnabled(false);
                mEtLegalName.setEnabled(false);
                mEtCertificateNo.setEnabled(false);
                mTvSex.setClickable(false);
                mTvDocumentType.setClickable(false);
                mLlLocation.setClickable(false);
                mIvFacade.setClickable(false);
                mIvEnvironment.setClickable(false);
                mIvIdPhoto.setClickable(false);
                mIvIdPhotoBack.setClickable(false);
                mIvBusinessLicense.setClickable(false);
                mIvLicence.setClickable(false);
            } else if (entity.getStatus() == 2) {  //????????????
                mToolbarTitle.setText("????????????");
            }
            mEtName.setText(entity.getContact_name());
            mEtPhone.setText(entity.getMobile());
            mEtStoreName.setText(entity.getShop_name());

            province = entity.getLocation();

            mTvAddress.setText(entity.getAddress());
            facade = entity.getFacade_image();
            GlideUtil.create().loadLongImage(this, entity.getFacade_image(), mIvFacade);//?????????
            environment = entity.getInterior_image();
            GlideUtil.create().loadLongImage(this, entity.getInterior_image(), mIvEnvironment);//????????????
            mEtLegalName.setText(entity.getCorporation());
            mSex = entity.getGender();
            mTvSex.setText(entity.getGender() == 0 ? "???" : "???");
            document_type = entity.getCertificate_type();
            mTvDocumentType.setText(entity.getCertificate_type() == 1 ? "?????????" : "");
            mEtCertificateNo.setText(entity.getCertificate_no());
            IdPhoto = entity.getCertificate_image1();
            GlideUtil.create().loadLongImage(this, entity.getCertificate_image1(), mIvIdPhoto);//???????????????
            IdPhotoBack = entity.getCertificate_image2();
            GlideUtil.create().loadLongImage(this, entity.getCertificate_image2(), mIvIdPhotoBack);//???????????????
            BusinessLicense = entity.getBusiness_license();
            GlideUtil.create().loadLongImage(this, entity.getBusiness_license(), mIvBusinessLicense);//????????????
            Licence = entity.getPermit();
            GlideUtil.create().loadLongImage(this, entity.getPermit(), mIvLicence);//?????????
            lng = entity.getLng();
            lat = entity.getLat();
        }
    }

    @Override
    public void OnShopapplyForEditSuccess() {
        StartActivity();
    }

    public void StartActivity() {
        ApplicationCompletedActivity.start(this, mEtName.getText().toString(), mEtStoreName.getText().toString(), mEtPhone.getText().toString(), true);
    }

    @Override
    public void onNetError() {

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
                        } else if (type == IDCARD) {
                            IdPhoto = result.get(0).getCompressPath();
                            GlideUtil.create().loadLongImage(ApplicationMaterialsActivity.this, IdPhoto, mIvIdPhoto);
                        } else if (type == IDCARD_BACK) {
                            IdPhotoBack = result.get(0).getCompressPath();
                            GlideUtil.create().loadLongImage(ApplicationMaterialsActivity.this, IdPhotoBack, mIvIdPhotoBack);
                        } else if (type == BUSINESS_LICENSE) {
                            BusinessLicense = result.get(0).getCompressPath();
                            GlideUtil.create().loadLongImage(ApplicationMaterialsActivity.this, BusinessLicense, mIvBusinessLicense);
                        } else if (type == LICENSE) {
                            Licence = result.get(0).getCompressPath();
                            GlideUtil.create().loadLongImage(ApplicationMaterialsActivity.this, Licence, mIvLicence);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }


    /**
     * ????????????
     *
     * @return
     */
    private ImageFileCompressEngine getCompressFileEngine() {
        return new ImageFileCompressEngine();
    }


    /**
     * ???????????????
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
        ToastUtil.showToast(message);
    }


    /**
     * Android6.0??????????????????????????????
     */
    private void requestPermission() {
        PermissionDialog.requestLeboPermissions(this, new PermissionDialog.PermissionCallBack() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent(ApplicationMaterialsActivity.this, SelectAddressByMapActivity.class);
                startActivityForResult(intent, 99);
            }

            @Override
            public void onFailure() {

            }

            @Override
            public void onAlwaysFailure() {
                PermissionDialog.showDialog(ApplicationMaterialsActivity.this, "android.permission.ACCESS_FINE_LOCATION");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            province = data.getStringExtra("province");
            address = data.getStringExtra("address");
            LatLng latLng = data.getParcelableExtra("location");
            if (latLng != null) {
                double[] gps = MapUtil.transformBD09ToWGS84(latLng.longitude, latLng.latitude);
                lng = gps[0] + "";
                lat = gps[1] + "";
            }
            mTvAddress.setText(address);
        }
    }
}