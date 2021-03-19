package com.fillumina.formio.gen;

import java.util.Set;
import java.util.function.Consumer;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class JSONUtils {

    public static JSONObject clone(JSONObject jsonObject) {
        return new JSONObject(jsonObject.toString());
    }

    public static JSONObject merge(JSONObject obj1, JSONObject obj2) {
        JSONObject merged = new JSONObject(obj1, JSONObject.getNames(obj1));
        for (String key : JSONObject.getNames(obj2)) {
            merged.put(key, obj2.get(key));
        }
        return merged;
    }

    /**
     * Adds the {@code propertyName} property to the {@code json} object according to what
     * specified in {@code values} which is an object where:
     * <ul>
     * <li>key: specify the object to modify
     * <li>value: specify the value to add to the property {@code propertyName}
     * </ul>
     *
     * @param json can be a JSONObject or a JSONArray
     * @param values the values that are to set (i.e. {@code { "alpha": true, "beta": false } })
     * @param propertyName the properties in the target object that should be set with the value
     *        (i.e. {@code 'disable'}).
     */
    public static void setValuesToProperty(Object json, JSONObject values, String propertyName) {
        Set<String> valueKeys = values.keySet();
        JSONUtils.deepTraverse(json, jobj -> {
            if (jobj.keySet().contains("key") ) {
                String propName = jobj.getString("key");
                if (valueKeys.contains(propName)) {
                    Object valueToSet = values.get(propName);
                    jobj.put(propertyName, valueToSet);
                }
            }
        });
    }

    public static void deepTraverse(Object obj, Consumer<JSONObject> nodeConsumer) {
        if (obj instanceof JSONObject) {
            deepTraverseObject((JSONObject) obj, nodeConsumer);
        } else if (obj instanceof JSONArray) {
            deepTraverseArray((JSONArray) obj, nodeConsumer);
        }
    }

    public static void deepTraverseObject(JSONObject obj, Consumer<JSONObject> nodeConsumer) {
        Object valueToSet = null;
        for (String name : obj.keySet()) {
            Object prop = obj.get(name);
            if (prop instanceof JSONArray) {
                deepTraverseArray((JSONArray) prop, nodeConsumer);
            } else if (prop instanceof JSONObject) {
                deepTraverseObject((JSONObject) prop, nodeConsumer);
            }
        }
        nodeConsumer.accept(obj);
    }

    public static void deepTraverseArray(JSONArray array, Consumer<JSONObject> nodeConsumer) {
        for (Object item : array) {
            if (item instanceof JSONObject) {
                deepTraverseObject((JSONObject) item, nodeConsumer);
            } else if (item instanceof JSONArray) {
                deepTraverseArray((JSONArray) item, nodeConsumer);
            }
        }
    }
}
