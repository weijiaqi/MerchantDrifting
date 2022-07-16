package com.merchant.drifting.app.receiver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;


import com.merchant.drifting.util.ToastUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @Description:
 * @Author: wjq
 * @CreateDate: 2022/2/21 10:54
 */
public class NetworkUtil {
    private static final String[] NETWORK = {"none", "WIFI", "2G", "3G", "4G", "mobile"};
    // 没有网络连接
    private static final int NETWORN_NONE = 0;
    // wifi连接
    private static final int NETWORN_WIFI = 1;
    // 手机网络数据连接类型
    private static final int NETWORN_2G = 2;
    private static final int NETWORN_3G = 3;
    private static final int NETWORN_4G = 4;
    private static final int NETWORN_MOBILE = 5;
    private static final String TAG = NetworkUtil.class.getSimpleName();

    /**
     * 判断是否有网络可用（无提示语）
     *
     * @param context
     * @return true 有网
     */
    public static boolean isNetAvailable(Context context) {
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        } else {
            return false;
        }
    }

    /**
     * 检测网络状态（当前无网络提示语）
     *
     * @param context
     * @return true 有网
     */
    public static boolean checkNetworkState(Context context) {
        if (!isNetAvailable(context)) {
            ToastUtil.showToast("网络连接失败");
            return false;
        }
        return true;
    }

    /**
     * 获取可用的网络信息
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取网络类型
     */
    public static String getNetworkType(Context ctx) {
        return NETWORK[getNetworkState(ctx)];
    }

    /**
     * 是否4G或者Wifi
     */
    public static boolean is4GOrWifi(Context ctx) {
        int networkState = getNetworkState(ctx);
        return networkState == NETWORN_4G || networkState == NETWORN_WIFI;
    }

    /**
     * 是否4G网络
     */
    public static boolean is4G(Context ctx) {
        return getNetworkState(ctx) == NETWORN_4G;
    }

    /**
     * 是否Wifi网络
     */
    public static boolean isWifi(Context ctx) {
        return getNetworkState(ctx) == NETWORN_WIFI;
    }

    /**
     * 是否手机网络
     */
    public static boolean isMobile(Context ctx) {
        int networkState = getNetworkState(ctx);
        return networkState == NETWORN_MOBILE || networkState == NETWORN_2G
                || networkState == NETWORN_3G || networkState == NETWORN_4G;
    }

    /**
     * 获取网络类型
     */
    public static int getNetworkState(Context context) {
        // 获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // 如果当前没有网络
        if (connManager == null) {
            return NETWORN_NONE;
        }

        // 获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORN_NONE;
        }

        // 判断是不是连接的是不是WIFI
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORN_WIFI;
                }
            }
        }

        // 如果不是WIFI，则判断当前连接的是运营商的哪种网络2G、3G、4G等
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        // 2G
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2G
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2G
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2G
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORN_2G;
                        // 3G
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3G
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORN_3G;
                        // 4G
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORN_4G;
                        default:
                            // 中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA")
                                    || strSubTypeName.equalsIgnoreCase("WCDMA")
                                    || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return NETWORN_3G;
                            } else {
                                return NETWORN_MOBILE;
                            }
                    }
                }
            }
        }
        return NETWORN_NONE;
    }

    /**
     * 获取WiFi名称
     */
    public static String getIP(Context context) {
        String ip = "";
        try {

            // 判断是否是有线网络
            boolean eth0 = false;
            boolean wifi = false;
            boolean mobile = false;
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String type = networkInfo.getTypeName();
                if (type.equalsIgnoreCase("Ethernet")) {
                    eth0 = true;
                } else if (type.equalsIgnoreCase("WIFI")) {
                    wifi = true;
                } else if (type.equalsIgnoreCase("MOBILE")) {
                    mobile = true;
                }
            }

            // 在有些设备上wifi和有线同时存在，获得的ip会有两个
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            if (en == null) {
                return ip;
            }
            while (en.hasMoreElements()) {
                NetworkInterface element = en.nextElement();
                Enumeration<InetAddress> inetAddresses = element.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        ip = inetAddress.getHostAddress().toString();
                        Log.i(TAG, "getIPAddress: " + ip);
                        if (eth0) {
                            if (element.getDisplayName().equals("eth0")) {
                                return ip;
                            }
                        } else if (wifi) {
                            if (element.getDisplayName().equals("wlan0")) {
                                return ip;
                            }
                        } else if (mobile) {
                            return ip;
                        }
                        break;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.w(TAG, ex);
        }
        return ip;
    }

    /* 获取WiFi的SSID */
    /* 判断当前网路有线还是WiFi */
    public static String getNetWorkName(Context context) {
        String wired_network = "有线网络";
        String wireless_network = "无线网络";
        String mobile_network = "移动网络";
        String net_error = "网络错误";

        try {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String type = networkInfo.getTypeName();
                if (type.equalsIgnoreCase("Ethernet")) {
                    return wired_network;
                } else if (type.equalsIgnoreCase("WIFI")) {
                    String tmpssid = getWifiSSID(context);
                    if (tmpssid.contains("unknown") || tmpssid.contains("0x")) {
                        tmpssid = wireless_network;
                    }
                    return tmpssid;
                } else if (type.equalsIgnoreCase("MOBILE")) {
                    return mobile_network;
                } else {
                    return wired_network;
                }
            } else {
                String apName = getAPName(context);
                if (!TextUtils.isEmpty(apName)) {
                    return apName;
                }
                return net_error;
            }
        } catch (Exception e) {
            Log.w(TAG, e);
            return net_error;
        }
    }

    private static String getAPName(Context context) {
        if (!isWifiApOpen(context)) {
            return "";
        }
        try {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            Method method = manager.getClass().getDeclaredMethod("getWifiApConfiguration");
            WifiConfiguration configuration = (WifiConfiguration) method.invoke(manager);
            return configuration.SSID;
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        return "";
    }

    public static boolean isWifiApOpen(Context context) {
        try {
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            Method method = manager.getClass().getDeclaredMethod("getWifiApState");
            int state = (int) method.invoke(manager);
            Field field = manager.getClass().getDeclaredField("WIFI_AP_STATE_ENABLED");
            int value = (int) field.get(manager);
            if (state == value) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.w(TAG, e);
        }
        return false;
    }

    private static String getWifiSSID(Context context) {
        String ssid = "unknown id";
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifiManager.getConnectionInfo();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                return info.getSSID();
            } else {
                return info.getSSID().replace("\"", "");
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo.isConnected()) {
                if (networkInfo.getExtraInfo() != null) {
                    return networkInfo.getExtraInfo().replace("\"", "");
                }
            }
        }
        return ssid;
    }
}
