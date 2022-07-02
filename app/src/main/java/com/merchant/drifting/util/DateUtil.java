package com.merchant.drifting.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lzy on 2018/12/20.
 */

public class DateUtil {
    /**
     * unix转MM-dd HH:mm
     *
     * @param unixTime
     * @return
     */
    public static String unixToMDHM(String unixTime) {
        try {
            String s = Long.parseLong(unixTime) * 1000 + "";
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            if (TextUtils.isEmpty(unixTime)) {
                unixTime = "";
            }
            return unixTime;
        }
    }

    /**
     * unix时间戳转换成yyyy年MM月格式
     *
     * @param unixTime
     * @return
     */
    public static String unixToYM(String unixTime) {
        try {
            String s = (Long.parseLong(unixTime) * 1000) + "";
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            if (TextUtils.isEmpty(unixTime)) {
                unixTime = "";
            }
            return unixTime;
        }
    }

    /**
     * unix时间戳转换成MM-dd HH:mm样式
     *
     * @param unixTime
     * @return
     */
    public static String unixToDateMDH(String unixTime) {
        try {
            String s = (Long.parseLong(unixTime) * 1000) + "";
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            if (TextUtils.isEmpty(unixTime)) {
                unixTime = "";
            }
            return unixTime;
        }
    }

    /**
     * unix时间戳转换成yyyy-MM-dd HH:mm:ss
     *
     * @param unixTime
     * @return
     */
    public static String unxiToDateYMD(String unixTime) {
        try {
            String s = Long.parseLong(unixTime) * 1000 + "";
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            if (TextUtils.isEmpty(unixTime)) {
                unixTime = "";
            }
            return unixTime;
        }
    }



    /**
     * unix时间戳转换成yyyy-MM-dd HH:mm
     *
     * @param unixTime
     * @return
     */
    public static String unxiToDateYMDHM(String unixTime) {
        try {
            String s = Long.parseLong(unixTime) * 1000 + "";
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            if (TextUtils.isEmpty(unixTime)) {
                unixTime = "";
            }
            return unixTime;
        }
    }






    /**
     * unix时间戳转换成HH:mm
     *
     * @param unixTime
     * @return
     */
    public static String unxiToDateHM(String unixTime) {
        try {
            String s = Long.parseLong(unixTime) * 1000 + "";
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            if (TextUtils.isEmpty(unixTime)) {
                unixTime = "";
            }
            return unixTime;
        }
    }

    /**
     * unix时间戳转换成yyyy-MM-dd 星期E
     *
     * @param unixTime
     * @return
     */
    public static String unixToDateWeek(String unixTime) {
        try {
            String s = Long.parseLong(unixTime) * 1000 + "";
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd EEEE");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception e) {
            if (TextUtils.isEmpty(unixTime)) {
                unixTime = "";
            }
            return unixTime;
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss时间转换成Date对象
     *
     * @param time
     * @return
     */
    public static Date getDateByString(String time) {
        Date date = null;
        if (time == null) {
            return date;
        }
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间戳转xx前
     *
     * @param unixTime
     * @return
     */
    public static String getTimeStamp(String unixTime) {
        try {
            return DateUtil.getShortTime(DateUtil.unxiToDateYMD(unixTime));
        } catch (Exception e) {
            if (TextUtils.isEmpty(unixTime)) {
                unixTime = "";
            }
            return unixTime;
        }
    }

    /**
     * yyyy-MM-dd HH:mm:ss时间转换成XX前
     *
     * @param time
     * @return
     */
    public static String getShortTime(String time) {
        try {
            String shortString = null;
            long now = Calendar.getInstance().getTimeInMillis();
            Date date = getDateByString(time);
            if (date == null) {
                return shortString;
            }
            long delTime = (now - date.getTime()) / 1000;
            if (delTime > 30 * 24 * 60 * 60) {
                if (time.length() > 10) {
                    shortString = time.substring(0, 10);
                } else {
                    shortString = "";
                }
            } else if (delTime > 24 * 60 * 60) {
                shortString = (int) (delTime / (24 * 60 * 60)) + "天前";
            } else if (delTime > 60 * 60) {
                shortString = (int) (delTime / (60 * 60)) + "小时前";
            } else if (delTime > 60) {
                shortString = (int) (delTime / (60)) + "分钟前";
            } else if (delTime > 1) {
//                shortString = delTime + "刚刚";
                shortString = "刚刚";
            } else {
                shortString = "刚刚";
            }
            return shortString;
        } catch (Exception e) {
            if (TextUtils.isEmpty(time)) {
                time = "";
            }
            return time;
        }
    }


    //根据秒数转化为时分秒   00:00:00
    public static String getTime(int second) {
        if (second < 10) {
            return "00:0" + second;
        }
        if (second < 60) {
            return "00:" + second;
        }
        if (second < 3600) {
            int minute = second / 60;
            second = second - minute * 60;
            if (minute < 10) {
                if (second < 10) {
                    return "0" + minute + ":0" + second;
                }
                return "0" + minute + ":" + second;
            }
            if (second < 10) {
                return minute + ":0" + second;
            }
            return minute + ":" + second;
        }
        int hour = second / 3600;
        int minute = (second - hour * 3600) / 60;
        second = second - hour * 3600 - minute * 60;
        if (hour < 10) {
            if (minute < 10) {
                if (second < 10) {
                    return "0" + hour + ":0" + minute + ":0" + second;
                }
                return "0" + hour + ":0" + minute + ":" + second;
            }
            if (second < 10) {
                return "0" + hour + minute + ":0" + second;
            }
            return "0" + hour + minute + ":" + second;
        }
        if (minute < 10) {
            if (second < 10) {
                return hour + ":0" + minute + ":0" + second;
            }
            return hour + ":0" + minute + ":" + second;
        }
        if (second < 10) {
            return hour + minute + ":0" + second;
        }
        return hour + minute + ":" + second;
    }


    //秒数转换成小时分钟
    public static String TimeRemaining(int second) {
        if (second < 60) {
            return "1分钟";
        }
        if (second<3600){
            int minute = second / 60;
            return minute+"分钟";
        }
        int hour = second / 3600;
        return hour+"小时";
    }
}
