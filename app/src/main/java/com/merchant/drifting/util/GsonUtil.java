package com.merchant.drifting.util;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * gson util
 */

public class GsonUtil {

    private GsonUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 将对象转成json格式
     *
     * @param object
     * @return String
     */
    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * 将json转成特定的cls的对象
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     * @throws JsonParseException
     */
    public static <T> T fromJson(String json, Class<T> cls) throws JsonParseException {
        Gson gson = new Gson();
        return gson.fromJson(json, cls);
    }

    /**
     * json字符串转成list
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> List<T> fromJsonList(String json, Class<T> cls) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * 把一个json的字符串转换成为一个包含POJO对象的List
     *
     * @param string
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String string, Class<T[]> cls) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(string, cls);
        return Arrays.asList(array);
    }

    /**
     * json字符串转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> fromJsonMapList(String gsonString) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
        }.getType());
    }

    /**
     * json字符串转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> fromJsonMap(String gsonString) {
        Gson gson = new Gson();
        return gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
    }

}
