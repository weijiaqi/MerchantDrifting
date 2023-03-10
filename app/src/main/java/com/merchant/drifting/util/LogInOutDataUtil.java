package com.merchant.drifting.util;


import com.merchant.drifting.data.event.LogInEvent;
import com.merchant.drifting.mvp.model.entity.LoginEntity;
import com.merchant.drifting.mvp.ui.activity.login.MerchantCenterActivity;
import com.merchant.drifting.storageinfo.Preferences;

import org.greenrobot.eventbus.EventBus;

/**
 * @Description:
 * @Author: wjq
 * @CreateDate: 2022/2/18 16:15
 */
public class LogInOutDataUtil {
    public static void successInSetData(LoginEntity entity) {
        Preferences.setAnony(true);
        Preferences.saveToken(entity == null ? "" : entity.getToken());
        Preferences.saveNickName(entity == null ? "" : entity.getBusiness_name());
        Preferences.savePhone(entity == null ? "" : entity.getMobile());
        Preferences.savePhoto(entity == null ? "" : entity.getProfile_photo());
    }

    public static void successOutClearData() {
        Preferences.clearUserLoginData();
    }
}
