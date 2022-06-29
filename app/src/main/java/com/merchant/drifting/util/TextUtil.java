package com.merchant.drifting.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;


import com.merchant.drifting.app.application.MerchantDriftingApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

/**
 * @author wjq
 * @date
 */
@SuppressLint("SetTextI18n")
public class TextUtil {

    public static  void  setRightImage(ImageView imageView,int drawable){
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(drawable);
    };

    /**
     * 关键字高亮变色
     *
     * @param context 上下文
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字
     * @return 结果SpannableString
     */
    public static SpannableString matcherSearchTitle(Context context, int color, String text, String... keyword) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keyword.length; i++) {
            if (!TextUtils.isEmpty(keyword[i])) {
                keyword[i] = escapeExprSpecialWord(keyword[i]);
                text = escapeExprSpecialWord(text);
                if (text.contains(keyword[i]) && !TextUtils.isEmpty(keyword[i])) {
                    try {
                        Pattern p = Pattern.compile(keyword[i]);
                        Matcher m = p.matcher(s);
                        while (m.find()) {
                            int start = m.start();
                            int end = m.end();
                            //注意：请使用ContextCompat.getColor获取颜色值。
                            s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    } catch (Exception e) {
                        Timber.e("错误：" + e.toString());
                    }
                }
            }

        }
        return s;
    }

    /**
     * 关键字高亮变色
     *
     * @param context 上下文
     * @param color   变化的色值
     * @param text    文字
     * @param keyword 文字中的关键字
     * @return 结果SpannableString
     */
    public static SpannableString matcherSearchText(Context context, int color, String text, String keyword) {
        SpannableString ss = new SpannableString(text);

        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return ss;
    }

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return keyword
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String key : fbsArr) {
                if (keyword.contains(key)) {
                    keyword = keyword.replace(key, "\\" + key);
                }
            }
        }
        return keyword;
    }

    /**
     * 通过Html.fromHtml方式修改颜色(解决SDK版本问题)
     *
     * @param html
     * @return
     */
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @NonNull //当返回值为null时会出现警告
    public static String getText(@StringRes int str) {
        return getResource().getString(str);
    }

    public static Resources getResource() {
        return MerchantDriftingApplication.getContext().getResources();
    }


    /**
     * 字体颜色修改
     *
     * @param content
     * @param color
     * @return
     */
    public static String color(String content, int color) {
        return "<font color=\"#" + Integer.toHexString(color) + "\" >" + content + "</font>";
    }

    /**
     * 加粗字体
     *
     * @param content
     * @return
     */
    public static String bold(String content) {
        return "<b>" + content + "</b>";
    }

    /**
     * TextView判空
     *
     * @param content
     */
    public static String setText(String content) {
        if (!TextUtils.isEmpty(content)) {
            return content;
        }
        return "";
    }

    /**
     * TextView判空
     *
     * @param content
     */
    public static String defaultText(String content, String defaultText) {
        if (!TextUtils.isEmpty(content)) {
            return content;
        }
        return defaultText;
    }

    /**
     * TextView判断是否str1大于str2
     */
    public static int Comparae(String str1, String str2) {
        try {
            if (Integer.parseInt(str1) > Integer.parseInt(str2)) {
                return 1;
            } else if (Integer.parseInt(str1) < Integer.parseInt(str2)) {
                return -1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * TextView判空、整型字符串拼接
     *
     * @param content
     * @param args
     * @return
     */
    public static String setText(String content, Object... args) {
        if (args != null) {
            for (Object arg : args) {
                if (arg == null || arg.equals("")) {
                    return "";
                }
            }
            return String.format(content, args);
        }
        return "";
    }

    /**
     * TextView判空、整型字符串拼接
     *
     * @param textView
     * @param content
     * @param args
     */
    public static void setText(TextView textView, String content, Object... args) {
        if (textView != null && args != null) {
            for (Object arg : args) {
                if (arg == null || arg.equals("")) {
                    textView.setText("");
                    return;
                }
            }
            textView.setText(String.format(content, args));
        }
    }

    /**
     * TextView判空
     *
     * @param textView
     * @param content
     */
    public static void setText(TextView textView, int content) {
        if (textView != null) {
            textView.setText(content + "");
        }
    }

    /**
     * TextView判空
     *
     * @param textView
     * @param content
     */
    public static void setText(TextView textView, String content) {
        if (textView != null) {
            if (!TextUtils.isEmpty(content)) {
                textView.setText(content);
            } else {
                textView.setText("");
            }
        }
    }

    /**
     * 获取圆角中文空格
     */
    public static String getBlankSpace(int count) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 去除空格
     * @param str
     * @return
     */
    public static String replaceEnterAndSpace(String str) {
        String result = str.replace("\n", "");
        result = result.replace(" ", "");
        return result;
    }

    /**
     * 搜索颜色变红
     *
     * @param context
     * @param textView
     * @param content
     * @param searchContent
     */
    public static void setSearchText(Context context, TextView textView, String content, String searchContent, int color) {
        if (!TextUtils.isEmpty(content)) {
            String[] keyWords = searchContent.split("");
            textView.setText(matcherSearchTitle(context, color, content, keyWords));
        } else {
            textView.setText("");
        }
    }

    /**
     * 搜索颜色变红
     *
     * @param context
     * @param textView
     * @param content
     * @param searchContent
     */
    public static void setHighlightText(Context context, TextView textView, String content, String searchContent, int color) {
        if (!TextUtils.isEmpty(content)) {
            String[] keyWords = searchContent.split("");
            textView.setText(matcherSearchTitle(context, color, content, keyWords));
        } else {
            textView.setText("");
        }
    }

    /**
     * 判断是否是http地址
     *
     * @param url
     * @return
     */
    public static boolean isHttpUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) {
            return true;
        }
        return false;
    }

    /**
     * 只允许输入两位小数的数字
     *
     * @return
     */
    public static void watchEditView(EditText mEdit) {
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //删除.后面超过两位的数字
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        mEdit.setText(s);
                        mEdit.setSelection(s.length());
                    }
                }

                //如果.在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mEdit.setText(s);
                    mEdit.setSelection(2);
                }

                //如果起始位置为0并且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mEdit.setText(s.subSequence(0, 1));
                        mEdit.setSelection(1);
                        return;
                    }
                }

                //不允许输入两个"."
                if (s.toString().trim().length() > 2) {
                    int firstIndex = s.toString().trim().indexOf(".");
                    int lastIndex = s.toString().trim().lastIndexOf(".");
                    if (lastIndex - firstIndex == 1) {
                        mEdit.setText(s.subSequence(0, lastIndex));
                        mEdit.setSelection(lastIndex);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
