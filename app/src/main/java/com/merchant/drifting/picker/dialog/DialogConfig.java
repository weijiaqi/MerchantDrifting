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

import com.merchant.drifting.picker.dialog.DialogColor;
import com.merchant.drifting.picker.dialog.DialogStyle;

/**
 * @author 贵州山魈羡民 (1032694760@qq.com)
 * @since 2021/9/16 15:55
 */
public final class DialogConfig {
    private static int dialogStyle = DialogStyle.Default;
    private static DialogColor dialogColor = new DialogColor();
    private static int type;
    private DialogConfig() {
        super();
    }

    public static void setDialogStyle(@DialogStyle int style) {
        dialogStyle = style;
    }

    @DialogStyle
    public static int getDialogStyle() {
        return dialogStyle;
    }

    public static void setDialogColor(DialogColor color) {
        dialogColor = color;
    }


    public static void setDialogType(int types) {
        type = types;
    }

    public static int getDialogType() {
        return type;
    }


    public static DialogColor getDialogColor() {
        if (dialogColor == null) {
            dialogColor = new DialogColor();
        }
        return dialogColor;
    }

}
