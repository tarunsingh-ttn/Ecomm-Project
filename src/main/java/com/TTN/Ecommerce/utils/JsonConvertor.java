package com.TTN.Ecommerce.utils;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonConvertor {
    public static Map<String,String> JSONtoMap(JSONObject jsonObject) {
        Map<String, String> map = new HashMap<>();
        Iterator<String> keysItr = jsonObject.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            String value = "";
            try {
                value = value + jsonObject.get(key);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            map.put(key, value);
        }
        return map;
    }


}
