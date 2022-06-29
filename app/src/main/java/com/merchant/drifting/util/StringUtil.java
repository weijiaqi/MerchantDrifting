package com.merchant.drifting.util;


import android.text.TextUtils;
import android.widget.CheckBox;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    /**
     * @description 勾选框
     */
    public static String checkProtocol(CheckBox checkBox) {
        String result = null;
        if (!checkBox.isChecked()) {
            result = "请阅读并勾选用户协议!";
        }
        return result;
    }

    /**
     * @description 手机号校验
     */
    public static String checkMobile(String mobile) {
        String result = null;
        if (isEmpty(mobile)) {
            result = "请输入手机号!";
        } else if (!VerifyUtil.verifyMobile(mobile)) {
            result = "请输入正确的手机号";
        }
        return result;
    }


    /**
     * @description 提现金额比较
     */
    public static String CompareMoney(String money, String money2) {
        String result = null;
        if (Double.parseDouble(money) > Double.parseDouble(money2)) {
            result = "可提现金额不足!";
        }
        return result;
    }


    /**
     * 判断字符串非空判断
     *
     * @param str
     * @return true空 false非空
     */
    public static boolean isEmpty(String str) {
        if (str != null && str.length() != 0) {
            return false;
        }
        return true;
    }

    public static String formatNullString(String input) {
        if (TextUtils.isEmpty(input) || TextUtils.equals("null", input.toLowerCase())) {
            return "";
        }
        return input;
    }


    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(long time) {
        try {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("CCT"));
            Date date = new Date(time * 1000);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return getCurrentTime();
        }
    }

    /**
     * 将时间戳转换为时间
     */
    public static long stampToLong(String time, String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date date = null;
            date = formatter.parse(time);
            if (date == null) {
                return 0;
            } else {
                long currentTime = date.getTime();
                return currentTime;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        long l = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * MD5加密
     */
    public static String md5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = new byte[0];
        try {
            byteArray = inStr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 保留两位小数点
     *
     * @param frontValue
     * @return
     */
    public static String frontValue(double frontValue) {
        DecimalFormat myformat = new DecimalFormat("0.00");
        String str = myformat.format(frontValue);
        return str;
    }


    /**
     * 逗号隔开保留两位小数点
     *
     * @param frontValue
     * @return
     */
    public static String frontCDecimalValue(double frontValue) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern(",##0.00");
        myformat.setRoundingMode(RoundingMode.DOWN);
        String str = myformat.format(frontValue);
        return str;
    }


    /**
     * 逗号隔开
     *
     * @param frontValue
     * @return
     */
    public static String frontCommaValue(double frontValue) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern(",##0");
        myformat.setRoundingMode(RoundingMode.DOWN);
        String str = myformat.format(frontValue);
        return str;
    }


    //漂流类型拼接
    public static String getCupNama(int explore_id, String sku_name, int num) {
        String name = null;
        switch (explore_id) {
            case 0:
                if (num > 1) {
                    name = sku_name + "*" + num;
                } else {
                    name = sku_name;
                }
                break;
            case 1:
                name = "传递漂-";
                break;
        }
        if (explore_id == 0) {
            return name;
        } else {
            return name + sku_name;
        }
    }
}
