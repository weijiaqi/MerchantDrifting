package com.merchant.drifting.picker;/*
 * Copyright (c) 2016-present 贵州纳雍穿青人李裕江<1032694760@qq.com>
 *
 * The software is licensed under the Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *     http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v2 for more details.
 */


import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.merchant.drifting.picker.dialog.ModalDialog;
import com.merchant.drifting.picker.widget.DateWheelLayout;


/**
 * 日期选择器
 *
 * @author 贵州山野羡民（1032694760@qq.com）
 * @since 2021/6/5 18:17
 */
@SuppressWarnings("unused")
public class DatePicker extends ModalDialog {
    protected DateWheelLayout wheelLayout;
    private OnDatePickedListener onDatePickedListener;
    private OnDatePickedSelectedListener onDatePickedSelectedListener;
    public DatePicker(@NonNull Activity activity) {
        super(activity);
    }

    public DatePicker(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    @NonNull
    @Override
    protected View createBodyView() {
        wheelLayout = new DateWheelLayout(activity);
        return wheelLayout;
    }

    @Override
    protected void onCancel(int type) {
        if (onDatePickedSelectedListener != null) {
            onDatePickedSelectedListener.onDatePicked(type);
        }
    }

    @Override
    protected void onOk() {
        if (onDatePickedListener != null) {
            String year = wheelLayout.getSelectedYear()+"";
            String month = wheelLayout.getSelectedMonth()<10?"0"+wheelLayout.getSelectedMonth():wheelLayout.getSelectedMonth()+"";
            String day = wheelLayout.getSelectedDay()<10?"0"+wheelLayout.getSelectedDay():wheelLayout.getSelectedDay()+"";
            onDatePickedListener.onDatePicked(year, month, day);
        }
    }

    public void setOnDatePickedSelectedListener(OnDatePickedSelectedListener onDatePickedSelectedListener) {
        this.onDatePickedSelectedListener = onDatePickedSelectedListener;
    }


    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        this.onDatePickedListener = onDatePickedListener;
    }

    public final DateWheelLayout getWheelLayout() {
        return wheelLayout;
    }

}
