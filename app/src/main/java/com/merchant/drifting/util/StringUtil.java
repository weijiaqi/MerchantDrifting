package com.merchant.drifting.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.CheckBox;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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


    //数组逗号拼接
    public static String addlist(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }



    /**
     * 如果版本1 大于 版本2 返回true 否则返回fasle 支持 2.2 2.2.1 比较
     * 支持不同位数的比较  2.0.0.0.0.1  2.0 对比
     *
     * @param v1 版本服务器版本 " 1.1.2 "
     * @param v2 版本 当前版本 " 1.2.1 "
     * @return ture ：需要更新 false ： 不需要更新
     */
    public static boolean compareVersions(String v1, String v2) {
        //判断是否为空数据
        if (TextUtils.equals(v1, "") || TextUtils.equals(v2, "")) {
            return false;
        }

        if (v1!=null&v2!=null){

            String[] str1 = v1.split("\\.");
            String[] str2 = v2.split("\\.");

            if (str1.length == str2.length) {
                for (int i = 0; i < str1.length; i++) {
                    if (Integer.parseInt(str1[i]) > Integer.parseInt(str2[i])) {
                        return true;
                    } else if (Integer.parseInt(str1[i]) < Integer.parseInt(str2[i])) {
                        return false;
                    } else if (Integer.parseInt(str1[i]) == Integer.parseInt(str2[i])) {

                    }
                }
            } else {
                if (str1.length > str2.length) {
                    for (int i = 0; i < str2.length; i++) {
                        if (Integer.parseInt(str1[i]) > Integer.parseInt(str2[i])) {
                            return true;
                        } else if (Integer.parseInt(str1[i]) < Integer.parseInt(str2[i])) {
                            return false;

                        } else if (Integer.parseInt(str1[i]) == Integer.parseInt(str2[i])) {
                            if (str2.length == 1) {
                                continue;
                            }
                            if (i == str2.length - 1) {

                                for (int j = i; j < str1.length; j++) {
                                    if (Integer.parseInt(str1[j]) != 0) {
                                        return true;
                                    }
                                    if (j == str1.length - 1) {
                                        return false;
                                    }

                                }
                                return true;
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < str1.length; i++) {
                        if (Integer.parseInt(str1[i]) > Integer.parseInt(str2[i])) {
                            return true;
                        } else if (Integer.parseInt(str1[i]) < Integer.parseInt(str2[i])) {
                            return false;

                        } else if (Integer.parseInt(str1[i]) == Integer.parseInt(str2[i])) {
                            if (str1.length == 1) {
                                continue;
                            }
                            if (i == str1.length - 1) {
                                return false;

                            }
                        }

                    }
                }
            }
        }
        return false;
    }


    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }
}
