/*
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

package com.merchant.drifting.picker.dialog;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.core.graphics.ColorUtils;


import com.hjq.shape.view.ShapeTextView;
import com.merchant.drifting.R;

/**
 * @author 贵州山野羡民（1032694760@qq.com）
 * @since 2021/6/3 15:23
 */
@SuppressWarnings("unused")
public abstract class ModalDialog extends BottomDialog implements View.OnClickListener {
    protected View headerView;
    protected ShapeTextView cancelView;

    protected TextView okView;
    protected TextView titleView;
    protected View bodyView;
    protected View footerView;

    protected int type, status;

    public ModalDialog(@NonNull Activity activity) {
        super(activity, DialogConfig.getDialogStyle() == DialogStyle.Three
                ? R.style.DialogTheme_Fade : R.style.DialogTheme_Sheet);
    }

    public ModalDialog(@NonNull Activity activity, @StyleRes int themeResId) {
        super(activity, themeResId);
    }

    @Override
    public void onInit(@Nullable Bundle savedInstanceState) {
        super.onInit(savedInstanceState);
        if (DialogConfig.getDialogStyle() == DialogStyle.Three) {
            setWidth((int) (activity.getResources().getDisplayMetrics().widthPixels * 0.8f));
            setGravity(Gravity.CENTER);
        }
    }

    @Override
    protected boolean enableMaskView() {
        return DialogConfig.getDialogStyle() != DialogStyle.Three;
    }

    @NonNull
    @Override
    protected View createContentView() {
        LinearLayout rootLayout = new LinearLayout(activity);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setGravity(Gravity.CENTER);
        rootLayout.setPadding(0, 0, 0, 0);
        headerView = createHeaderView();
        if (headerView == null) {
            headerView = new View(activity);
            headerView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        rootLayout.addView(headerView);
        bodyView = createBodyView();
        rootLayout.addView(bodyView, new LinearLayout.LayoutParams(MATCH_PARENT, 0, 1.0f));
        footerView = createFooterView();
        if (footerView == null) {
            footerView = new View(activity);
            footerView.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        }
        rootLayout.addView(footerView);
        return rootLayout;
    }

    @Nullable
    protected View createHeaderView() {
        switch (DialogConfig.getDialogStyle()) {
            case DialogStyle.One:
                return View.inflate(activity, R.layout.dialog_header_style_default, null);
            case DialogStyle.Two:
                return View.inflate(activity, R.layout.dialog_header_style_2, null);
            default:
                return null;
        }


    }

    @Nullable
    protected View createTopLineView() {
        View view = new View(activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, (int) (1 * activity.getResources().getDisplayMetrics().density)));
        view.setBackgroundColor(DialogConfig.getDialogColor().topLineColor());
        return view;
    }

    @NonNull
    protected abstract View createBodyView();

    @Nullable
    protected View createFooterView() {
        switch (DialogConfig.getDialogStyle()) {
            case DialogStyle.One:
                return View.inflate(activity, R.layout.dialog_footer_style_default, null);
            default:
                return null;
        }

    }

    @CallSuper
    @Override
    protected void initView() {
        super.initView();
        int color = DialogConfig.getDialogColor().contentBackgroundColor();
        switch (DialogConfig.getDialogStyle()) {
            case DialogStyle.One:
                setBackgroundColor(CornerRound.No, color);
                break;
            case DialogStyle.Two:
                setBackgroundColor(CornerRound.Top, color);
                break;
            case DialogStyle.Three:
                setBackgroundColor(CornerRound.All, color);
                break;
            default:
                setBackgroundColor(CornerRound.No, color);
                break;
        }

        status = DialogConfig.getDialogStyle();
        cancelView = contentView.findViewById(R.id.dialog_modal_cancel);

        okView = contentView.findViewById(R.id.dialog_modal_ok);
        if (status == 1) {
            type = DialogConfig.getDialogType();
            cancelView.setTextColor(activity.getColor(R.color.white));
            if (type == 2) {
                cancelView.getShapeDrawableBuilder().setSolidColor(activity.getColor(R.color.color_f9)).intoBackground();
                cancelView.setText("按月选择");
            } else {
                cancelView.getShapeDrawableBuilder().setSolidColor(activity.getColor(R.color.color_42)).intoBackground();
                cancelView.setText("按日选择");
            }
        } else {
            titleView = contentView.findViewById(R.id.dialog_modal_title);
            if (titleView == null) {
                throw new IllegalArgumentException("Title view id not found");
            }
        }

        if (cancelView == null) {
            throw new IllegalArgumentException("Cancel view id not found");
        }

        if (okView == null) {
            throw new IllegalArgumentException("Ok view id not found");
        }

//        okView.setTextColor(DialogConfig.getDialogColor().okTextColor());
        cancelView.setOnClickListener(this);
        okView.setOnClickListener(this);
        maybeBuildEllipseButton();
    }

    private void maybeBuildEllipseButton() {

    }

    @Override
    public void setTitle(final @Nullable CharSequence title) {
        if (titleView != null) {
            titleView.post(new Runnable() {
                @Override
                public void run() {
                    titleView.setText(title);
                }
            });
        } else {
            super.setTitle(title);
        }
    }

    @Override
    public void setTitle(final int titleId) {
        if (titleView != null) {
            titleView.post(new Runnable() {
                @Override
                public void run() {
                    titleView.setText(titleId);
                }
            });
        } else {
            super.setTitle(titleId);
        }
    }

    @CallSuper
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialog_modal_cancel) {
            DialogLog.print("cancel clicked");
            if (status == 1) {
                if (type == 3) {
                    type = 2;
                    cancelView.getShapeDrawableBuilder().setSolidColor(activity.getColor(R.color.color_f9)).intoBackground();
                    cancelView.setText("按月选择");
                } else {
                    type = 3;
                    cancelView.getShapeDrawableBuilder().setSolidColor(activity.getColor(R.color.color_42)).intoBackground();
                    cancelView.setText("按日选择");
                }
                onCancel(type);
            } else {
                dismiss();
            }
        } else if (id == R.id.dialog_modal_ok) {
            DialogLog.print("ok clicked");
            onOk();
            dismiss();
        }
    }

    protected abstract void onCancel(int type);

    protected abstract void onOk();

    public final void setBodyWidth(@Dimension(unit = Dimension.DP) @IntRange(from = 50) int bodyWidth) {
        ViewGroup.LayoutParams layoutParams = bodyView.getLayoutParams();
        int width = WRAP_CONTENT;
        if (bodyWidth != WRAP_CONTENT && bodyWidth != MATCH_PARENT) {
            width = (int) (bodyView.getResources().getDisplayMetrics().density * bodyWidth);
        }
        layoutParams.width = width;
        bodyView.setLayoutParams(layoutParams);
    }

    public final void setBodyHeight(@Dimension(unit = Dimension.DP) @IntRange(from = 50) int bodyHeight) {
        ViewGroup.LayoutParams layoutParams = bodyView.getLayoutParams();
        int height = WRAP_CONTENT;
        if (bodyHeight != WRAP_CONTENT && bodyHeight != MATCH_PARENT) {
            height = (int) (bodyView.getResources().getDisplayMetrics().density * bodyHeight);
        }
        layoutParams.height = height;
        bodyView.setLayoutParams(layoutParams);
    }

    public final View getHeaderView() {
        if (headerView == null) {
            headerView = new View(activity);
        }
        return headerView;
    }


    public final View getBodyView() {
        return bodyView;
    }

    public final View getFooterView() {
        return footerView;
    }

    public final TextView getCancelView() {
        return cancelView;
    }

//    public final TextView getTitleView() {
//        return titleView;
//    }

    public final TextView getOkView() {
        return okView;
    }

}
