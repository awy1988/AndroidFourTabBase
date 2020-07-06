package com.demo.corelib.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.text.DateFormat;
import java.util.List;

/**
 * 个人中心模块工具类
 */
public class JsonUtils {


    /**
     * 将JSON对象转化为Bean对象
     */
    public static <T> T parseObject(String jsonStr, Class<T> tClass) {

        T bean;

        ObjectMapper objectMapper = new ObjectMapper();
        // 下面这句话的作用是：返回的JSON字符串中含有我们并不需要的字段时，进行忽略
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            bean = objectMapper.readValue(jsonStr, tClass);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return bean;
    }

    /**
     * 将JSON数组变为List对象
     */
    public static <T> List<T> parseArray(String jsonStr, Class<T> tClass) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, tClass);
        List<T> result;
        try {
            result = objectMapper.readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    /**
     * 将对象转JSON字符串
     */
    public static String toJsonString(Object object) {

        return toJsonString(object, null);
    }

    /**
     * 对象转JSON字符串，在有日期字段时，指定日期格式。
     * 如果不指定日期格式，则Jackson会默认将日期转换为毫秒数形式的时间戳。
     */
    public static String toJsonString(Object object, DateFormat dateFormat) {

        ObjectMapper objectMapper = new ObjectMapper();
        if (dateFormat != null) {
            objectMapper.setDateFormat(dateFormat);
        }
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;

    }

}
