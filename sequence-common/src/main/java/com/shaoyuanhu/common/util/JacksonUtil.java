package com.shaoyuanhu.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @Description: 使用Jackson实现的json工具类
 * @Author: ShaoYuanHu
 * @Date: 2017/12/3
 */
public class JacksonUtil {

    public static final ObjectMapper OM = new ObjectMapper();
    static{
        /**
         * 设置将时间转换成指定的格式
         */
        OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 将对象序列化为Json串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        try {
            return OM.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json串反序列化成对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz){
        try {
            return OM.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json串反序列化为ArrayList集合
     * @param json
     * @param object
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> readValuesAsArrayList(String json, Class<T> object) {
        try {
            return OM.readValue(json, OM.getTypeFactory().constructParametricType(ArrayList.class, object));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json串反序列化为ArrayList集合
     * @param json
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> readValuesAsArrayList(String json, TypeReference<ArrayList<T>> t) {
        try {
            return OM.readValue(json, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
