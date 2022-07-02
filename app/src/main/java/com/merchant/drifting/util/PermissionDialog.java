package com.merchant.drifting.util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.jess.arms.base.BaseDialog;
import com.merchant.drifting.mvp.ui.dialog.CurrencyDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * @description 动态权限
 */
public class PermissionDialog {
    private static PermissionDialog mPermissionUtils;
    private static List<String> list;

    private PermissionDialog() {
    }

    public static PermissionDialog create() {
        if (mPermissionUtils == null) {
            synchronized (PermissionDialog.class) {
                if (mPermissionUtils == null) {
                    mPermissionUtils = new PermissionDialog();
                }
            }
        }
        return mPermissionUtils;
    }



    /**
     * 存储权限
     *
     * @param activity           FragmentActivity
     * @param permissionCallBack 状态回调
     */
    public static void requestPermissions(Activity activity, PermissionCallBack permissionCallBack) {
        new RxPermissions((FragmentActivity) activity).requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            permissionCallBack.onSuccess();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            permissionCallBack.onFailure();
                        } else {
                            permissionCallBack.onAlwaysFailure();
                        }
                    }
                });
    }


    /**
     * 读取权限
     *
     * @param activity           FragmentActivity
     * @param permissionCallBack 状态回调
     */
    public static void externalReadStoragePermissions(Activity activity, PermissionCallBack permissionCallBack) {
        new RxPermissions((FragmentActivity) activity).requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            permissionCallBack.onSuccess();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            permissionCallBack.onFailure();
                        } else {
                            permissionCallBack.onAlwaysFailure();
                        }
                    }
                });
    }


    /**
     * 语音权限
     *
     * @param activity           FragmentActivity
     * @param permissionCallBack 状态回调
     */
    public static void requestAudioPermissions(Activity activity, PermissionCallBack permissionCallBack) {
        new RxPermissions((FragmentActivity) activity)
                .requestEachCombined(Manifest.permission.RECORD_AUDIO)
                .subscribe(permission -> {
                    if (permission.granted) {
                        permissionCallBack.onSuccess();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        permissionCallBack.onFailure();
                    } else {
                        permissionCallBack.onAlwaysFailure();
                    }
                });
    }


    /**
     * 位置权限
     *
     * @param activity           FragmentActivity
     * @param permissionCallBack 状态回调
     */
    public static void requestLeboPermissions(Activity activity, PermissionCallBack permissionCallBack) {
        new RxPermissions((FragmentActivity) activity)
                .requestEachCombined(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        permissionCallBack.onSuccess();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        permissionCallBack.onFailure();
                    } else {
                        permissionCallBack.onAlwaysFailure();
                    }
                });
    }


    /**
     * 拨打电话权限
     *
     * @param activity           FragmentActivity
     * @param permissionCallBack 状态回调
     */
    public static void requestPhonePermissions(Activity activity, PermissionCallBack permissionCallBack) {
        new RxPermissions((FragmentActivity) activity)
                .requestEachCombined(Manifest.permission.CALL_PHONE)
                .subscribe(permission -> {
                    if (permission.granted) {
                        permissionCallBack.onSuccess();
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        permissionCallBack.onFailure();
                    } else {
                        permissionCallBack.onAlwaysFailure();
                    }
                });
    }

    /**
     * 传权限参数
     */
    public static void showDialog(Activity activity, String permission) {
        list = new ArrayList<>();
        list.add(permission);
        showDialog(activity, list);
    }


    /**
     * 存储权限状态回调 onSuccess() 同意该权限  onFailure() 拒绝了该权限，没有选中『不再询问』 onAlwaysFailure() 用户拒绝了该权限，并且选中『不再询问』
     */
    public interface PermissionCallBack {
        void onSuccess();

        void onFailure();

        void onAlwaysFailure();
    }

    /**
     * 设置权限dialog
     */
    public static void showDialog(Activity activity, List<String> permissions) {
        String permissionsTitle = "存储权限管理";
        String permissionsDescribe = "请在“权限管理”中开启存储权限，开通后可正常使用相关功能";

        for (int i = 0; i < permissions.size(); i++) {
            switch (permissions.get(i)) {
                case "android.permission.CAMERA":
                    permissionsTitle = "相机权限管理";
                    permissionsDescribe = "请在“权限管理”中开启相机权限，开通后可正常使用相关功能";
                    break;
                case "android.permission.RECORD_AUDIO":
                    permissionsTitle = "录音权限管理";
                    permissionsDescribe = "请在“权限管理”中开启录音权限，开通后可正常使用相关功能";
                    break;
                case "android.permission.WRITE_EXTERNAL_STORAGE":
                    permissionsTitle = "存储权限管理";
                    permissionsDescribe = "请在“权限管理”中开启存储权限，开通后可正常使用相关功能";
                    break;
                case "android.permission.READ_EXTERNAL_STORAGE":
                    permissionsTitle = "读取权限管理";
                    permissionsDescribe = "请在“权限管理”中开启读取权限，开通后可正常使用相关功能";
                    break;
                case "android.permission.ACCESS_FINE_LOCATION":
                case "android.permission.ACCESS_COARSE_LOCATION":
                    permissionsTitle = "定位权限管理";
                    permissionsDescribe = "请在“权限管理”中开启定位权限，开通后可正常使用相关功能";
                    break;
            }
        }
        CurrencyDialog currencyDialog = new CurrencyDialog(activity);
        currencyDialog.show();
        currencyDialog.setType(2)
                .setText("去设置")
                .setTitleText(permissionsTitle, permissionsDescribe);
        currencyDialog.setCanceledOnTouchOutside(false);
        currencyDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        currencyDialog.setOnClickCallback(new BaseDialog.OnClickCallback() {
            @Override
            public void onClickType(int type) {
                if (type == CurrencyDialog.SELECT_FINISH) {
                    getSettingIntent(activity);
                }
            }
        });
    }

    /**
     * 获取应用详情页面intent
     *
     * @param activity activity_release_scheme
     */
    private static void getSettingIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(intent);
    }

    public static void showMessage(String message) {
        ToastUtil.showToast(message);
    }
}
