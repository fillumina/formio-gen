package com.fillumina.formio.gen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @see https://help.form.io/
 * @see https://github.com/formio/formio.js
 * @see https://formio.github.io/formio.js/app/examples/
 * @see https://formio.github.io/formio.js/docs/class/src/components/Components.js~Components.html
 * @see https://github.com/formio/formio.js/wiki/Form-JSON-Schema
 * 
 * // angular
 * @see https://github.com/formio/angular/wiki/Translations
 * @see https://stackoverflow.com/questions/60667350/angular-formio-set-language-in-formbuilder
 * 
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
// TODO insert data management containers (datagrid and datamap)
public class Form {
    
    private final JSONObject json;
    private final Map<String, Component<?,?>> allComponents;
    private final Map<String, Component<?,?>> components;

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
        components = new LinkedHashMap<>();
        allComponents = new LinkedHashMap<>();
    }
    
    public Form addComponent(Component<?,?> component) {
        final String key = component.getKey();
        components.put(key, component);
        allComponents.put(key, component);
        if (component instanceof Container) {
            ((Container)component).addComponentsToMap(allComponents);
        }
        return this;
    }
    
    /**
     * 
     * @param errors   list of errors returned (key, error)
     * @param jsonText json text to parse
     * @return the json object (check for errors before accepting it)
     */
    public FormResponse validateJson(String jsonText) {
        JSONObject jsonObject = new JSONObject(jsonText);

        Metadata metadata = new Metadata(jsonObject);
        
        boolean errorPresent = false;
        
        Map<String,ComponentValue> responseMap = new LinkedHashMap<>();
        
        JSONObject data = jsonObject.getJSONObject("data");
        for (Entry<String, Component<?,?>> entry : allComponents.entrySet()) {
            String key = entry.getKey();
            Component<?,?> component = entry.getValue();
            
            Object value;
            try {
                value = data.get(key);
            } catch (JSONException ex) {
                if (component.isRequired() && component.isValue()) {
                    errorPresent = true;
                    ComponentValue response = new ComponentValue(key, null, 
                            FormError.MISSING, key);
                    responseMap.put(key, response);
                }
                continue;
            }
            ComponentValue response = component.validate(value);
            if (response.isErrorPresent()) {
                errorPresent = true;
            }
            responseMap.put(key, response);
        }
        
        return new FormResponse(metadata, responseMap, errorPresent);
    }
    
    public JSONObject toJSONObject() {
        List<JSONObject> list = components.values().stream()
                .map(c -> c.toJSONObject())
                .collect(Collectors.toList());
        JSONArray array = new JSONArray();
        list.stream().forEach(j -> array.put(j));
        json.put("components", array);
        return json;
    }
}
