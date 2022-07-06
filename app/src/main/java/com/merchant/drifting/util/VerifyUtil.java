package com.merchant.drifting.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 校验
 * @Author: wjq
 * @CreateDate: 2022/2/18 15:20
 */
public class VerifyUtil {


    /**
     * 身份证号验证
     *
     * @param id_card
     * @return
     */
    public static boolean verifyIdCard(String id_card) {
        if (id_card.length() == 15) {
            return verify_15_IdCard(id_card);
        } else if (id_card.length() == 18) {
            return verify_18_IdCard(id_card);
        }
        return false;
    }


    /**
     * 粗略验证15位身份证号码
     *
     * @param id_card 身份证号码
     * @return
     */
    private static boolean verify_15_IdCard(String id_card) {
        //该正则粗略验证地区码、时间、顺序码、校验码
        String regex = "^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$";
        return Pattern.matches(regex, id_card);
    }

    /**
     * 验证18位身份证号码
     *
     * @param id_card 身份证号码
     * @return
     */
    private static boolean verify_18_IdCard(String id_card) {
        //该正则粗略验证地区码、时间、顺序码、校验码
        String regex = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        if (!Pattern.matches(regex, id_card)) {
            return false;
        }

        //验证地区码
        if (!verifyAreaCode(id_card.substring(0, 2))) {
            return false;
        }

        //验证出生日期码
        String birthday = id_card.substring(6, 10) + "-" + id_card.substring(10, 12) + "-" + id_card.substring(12, 14);
        if (!verifyBirthdayCode(birthday)) {
            return false;
        }

        //验证校验码
        return verifyCheckCode(id_card);
    }
    /**
     * 验证地区码
     *
     * @param area_code 地区码
     * @return
     */
    private static boolean verifyAreaCode(String area_code) {
        //初始化城市信息，用于验证地区码
        Integer[] city_code = new Integer[]{11, 12, 13, 14, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 50, 51, 52, 53, 54, 61, 62, 63, 64, 65, 71, 81, 82, 91};
        String[] city_name = new String[]{"北京", "天津", "河北", "山西", "内蒙古", "辽宁", "吉林", "黑龙江", "上海", "江苏", "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北", "湖南", "广东", "广西", "海南", "重庆", "四川", "贵州", "云南", "西藏", "陕西", "甘肃", "青海", "宁夏", "新疆", "台湾", "香港", "澳门", "国外"};
        Map<Integer, String> city_map = new HashMap<>();
        for (int index = 0; index < city_name.length; index++) {
            city_map.put(city_code[index], city_name[index]);
        }

        int key = Integer.valueOf(area_code);
        return city_map.containsKey(key);
    }

    /**
     * 验证出生日期码
     *
     * @param birthday_code 出生日期码
     * @return
     */
    private static boolean verifyBirthdayCode(String birthday_code) {
        try {
            SimpleDateFormat sdfParse = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdfParse.parse(birthday_code);
            SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (!birthday_code.equals(sdfFormat.format(date))) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 验证校验码
     *
     * @param id_card 身份证号
     * @return
     */
    private static boolean verifyCheckCode(String id_card) {
        int sum = 0;
        for (int card_index = 17; card_index >= 0; card_index--) {
            String number = "" + id_card.charAt(17 - card_index);
            if (Pattern.matches("^(x|X)$", number)) {
                number = "10";
            } else if (Pattern.matches("^\\d$", number)) {
                number = number;
            } else {
                return false;
            }
            sum += (Math.pow(2, card_index) % 11) * Integer.parseInt(number);
        }
        return sum % 11 == 1;
    }

    /**
     * 手机号验证
     *
     * @param mobiles
     * @return
     */
    public static boolean verifyMobile(String mobiles) {
         String regex = "[1][3456789]\\d{9}";
      // String regex = "^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[2-6])|(17[0-8])|(18[0-9])|(19[1|8-9]))\\d{8}$";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(mobiles);
         return matcher.matches();
    }

    /**
     * 验证码验证
     *
     * @param code
     * @return
     */
    public static boolean verifyCode(String code) {
        return code.length() >= 4 && code.length() <= 6;
    }

    /**
     * 将手机号中间四位变成 * 号
     *
     * @param phone
     * @return
     */
    public static String mobilePhoneReplace(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1 **** $2");
    }



    /**
     * 方法描述 隐藏银行卡号中间的字符串（使用*号），显示前四后四
     */
    public static String hideCardNo(String cardNo) {
        if(cardNo.length() < 9) {
            return cardNo;
        }
        int length = cardNo.length();

        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++) {
            if(i >= (length - afterLength)) {
                sb.append(cardNo.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }

        return sb.toString();
    }

    /**
     * 密码验证
     *
     * @param password
     * @return
     */
    public static boolean verifyPassword(String password) {
        if (password.length() < 8 || password.length() > 20) {
            return false;
        } else {
            return true;
        }
    }



}
