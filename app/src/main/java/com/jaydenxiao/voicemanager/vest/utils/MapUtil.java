package com.jaydenxiao.voicemanager.vest.utils;

import com.google.gson.Gson;

import java.util.Map;
import java.util.TreeMap;


public class MapUtil {


    /**
     * 使用 Map按key进行排序
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sortMap = new TreeMap<String, Object>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * map转string
     */
    public static String getMapToString(Map<String, Object> map) {
        try {
            Gson gson = new Gson();
            return gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
