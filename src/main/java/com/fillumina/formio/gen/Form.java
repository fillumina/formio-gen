package com.fillumina.formio.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fra
 */
public class Form {
    
    private final JSONObject json;
    private final List<Component<?>> components;

    /**
     * @see https://github.com/formio/formio.js/wiki/Form-JSON-Schema
     * @param name
     * @param title 
     */
    public Form(String name, String title, String id) {
        this.json = new JSONObject();
        json.put("title", title);
        json.put("type", "form");
        json.put("_id", id);
        json.put("display", "form");
        components = new ArrayList<>();
    }
    
    public Form addComponent(Component<?> component) {
        components.add(component);
        return this;
    }

    public JSONObject toJSONObject() {
        List<JSONObject> list = components.stream()
                .map(c -> c.toJSONObject())
                .collect(Collectors.toList());
        JSONArray array = new JSONArray();
        list.stream().forEach(j -> array.put(j));
        json.put("components", array);
        return json;
    }
}
