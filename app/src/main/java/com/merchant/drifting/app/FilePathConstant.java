package com.merchant.drifting.app;

import android.os.Environment;

import com.merchant.drifting.app.application.MerchantDriftingApplication;

import java.io.File;

public class FilePathConstant {

    // 文件存储目录
    public static final String MD_PATH = MerchantDriftingApplication.getContext().getFilesDir()+File.separator;

    // 临时文件存储目录
    public static final String TEMP_PATH = MD_PATH + File.separator + "temp";

    // 相机存储目录
    public static final String CAREMA_PATH = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
            + File.separator;
}
