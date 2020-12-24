package com.markyang.framework.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.markyang.framework.pojo.common.support.IgnoredJsonField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * JSON操作工具类
 *
 * @author yangchangliang
 * @version 1
 * @date 2020/3/20 10:38 上午 星期五
 */
@Slf4j
public final class JsonUtils {

    /**
     * 一般JSON操作
     */
    private static final Gson GENERAL_GSON;

    /**
     * 下划线转驼峰JSON
     */
    private static final Gson UNDERSCORE_GSON;

    /**
     * 美化JSON
     */
    private static final Gson PRETTY_GSON;

    /**
     * 字段排除策略
     */
    private static final ExclusionStrategy EXCLUSION_STRATEGY = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            IgnoredJsonField annotation = fieldAttributes.getAnnotation(IgnoredJsonField.class);
            return Objects.nonNull(annotation);
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    };

    static {
        GENERAL_GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
            .enableComplexMapKeySerialization()
            .addSerializationExclusionStrategy(EXCLUSION_STRATEGY)
            .create();

        UNDERSCORE_GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
            .enableComplexMapKeySerialization()
            .addSerializationExclusionStrategy(EXCLUSION_STRATEGY)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

        PRETTY_GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
            .enableComplexMapKeySerialization()
            .addSerializationExclusionStrategy(EXCLUSION_STRATEGY)
            .setPrettyPrinting()
            .create();
    }

    /**
     * 将JSON字符串转为对象
     * @param json json字符串
     * @param cls 类
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T fromJson(String json, Class<T> cls) {
        return GENERAL_GSON.fromJson(json, cls);
    }

    /**
     * 将JSON字符串转为对象
     * @param json json字符串
     * @param type 对象类型
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T fromJson(String json, Type type) {
        return GENERAL_GSON.fromJson(json, type);
    }

    /**
     * 将下划线风格的json字符串转为对象
     * @param json 下划线风格的json
     * @param cls 类
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T fromUnderscoreJson(String json, Class<T> cls) {
        return UNDERSCORE_GSON.fromJson(json, cls);
    }

    /**
     * 将下划线风格的json字符串转为对象
     * @param json 下划线风格的json
     * @param typeToken 对象类型
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T fromUnderscoreJson(String json, TypeToken<T> typeToken) {
        return UNDERSCORE_GSON.fromJson(json, typeToken.getType());
    }

    /**
     * 将对象转为JSON
     * @param obj 对象
     * @return json字符串
     */
    public static String toJson(Object obj) {
        return GENERAL_GSON.toJson(obj);
    }

    /**
     * 将对象转为下划线风格的JSON
     * @param obj 对象
     * @return json字符串
     */
    public static String toUnderscoreJson(Object obj) {
        return UNDERSCORE_GSON.toJson(obj);
    }

    /**
     * 将对象转为美化后的JSON
     * @param obj 对象
     * @return json字符串
     */
    public static String toPrettyJson(Object obj) {
        return PRETTY_GSON.toJson(obj);
    }

    /**
     * 从json对象中获取数组数据
     * @param jsonObject
     * @param listName
     * @param itemName
     * @return
     */
    public static JSONArray getJsonData(JSONObject jsonObject, String listName, String itemName) {
        JSONArray jsonData = null;
        Object list = jsonObject.get(listName);
        if(list != null) {
            JSONObject jsonList = (JSONObject) list;
            Object item = jsonList.get(itemName);
            if(item != null) {
                //如果此元素已存在,则转为jsonArray
                if(item instanceof JSONObject) {
                    JSONObject temp = (JSONObject) item;
                    jsonData = new JSONArray();
                    jsonData.add(temp);
                } else if(item instanceof JSONArray) {
                    jsonData = (JSONArray) item;
                }
            }
        }else{
            jsonData=new JSONArray();
        }
        return jsonData;
    }

    /**
     * 从json对象中获取对象数据
     * @param jsonObject
     * @param itemName
     * @return
     */
    public static JSONObject getJsonData(JSONObject jsonObject, String itemName) {
        JSONObject jsonData = null;
        Object list = jsonObject.get(itemName);
        if(list != null) {
            jsonData = (JSONObject) list;
        }
        return jsonData;
    }

    /**
     * JSON分组
     *
     * @param befortArray
     * @param byKey
     * @return JSONArray
     */
    public static JSONArray groupBy(JSONArray befortArray, String byKey) {
        if (befortArray.size() == 0) {
            return null;
        }
        Map<Object, String> map = new HashMap<Object, String>();
        JSONArray _resuleArray = new JSONArray();

        for (Object o : befortArray) {
            JSONObject _item = (JSONObject) o;
            // 如果JSON分组不存在
            if (StringUtils.isBlank(map.get(_item.get(byKey)))) {
                JSONObject _group_obj = new JSONObject();
                JSONArray _group_list = new JSONArray();
                _group_obj.put(byKey, _item.get(byKey));
                _group_list.add(_item);
                _group_obj.put("data", _group_list);
                map.put(_item.get(byKey), "exist");
                _resuleArray.add(_group_obj);
            } else {
                for (int _i = 0; _i < _resuleArray.size(); _i++) {
                    JSONObject _temp_obj = _resuleArray.getJSONObject(_i);
                    if (_temp_obj.get(byKey).equals(_item.get(byKey))) {
                        _temp_obj.getJSONArray("data").add(_item);
                    }
                }
            }

        }
        return _resuleArray;
    }


}
