package com.fillumina.formio.gen;

import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class JSONUtils {

    public static JSONObject clone(JSONObject jsonObject) {
        return new JSONObject(jsonObject, JSONObject.getNames(jsonObject));
    }

    public static JSONObject merge(JSONObject obj1, JSONObject obj2) {
        JSONObject merged = new JSONObject(obj1, JSONObject.getNames(obj1));
        for (String key : JSONObject.getNames(obj2)) {
            merged.put(key, obj2.get(key));
        }
        return merged;
    }
}
