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
    
    public Form addComponent(Component<?,?>... componentArray) {
        for (Component<?,?> component : componentArray) {
            final String key = component.getKey();
            components.put(key, component);
            if (component.isValue()) {
                allComponents.put(key, component);
            }
            if (component instanceof Container) {
                ((Container)component).addComponentsToMap(allComponents);
            }
        }
        return this;
    }
    
    /** Validates a json string against the rules specified in this form. */
    public FormResponse validateJson(String jsonText) {
        JSONObject json = new JSONObject(jsonText);
        return validateJson(json);
    }
    
    public FormResponse validateJson(JSONObject json) {
        boolean errorPresent = false;
        Map<String,ResponseValue> responseMap = new LinkedHashMap<>();

        for (Entry<String, Component<?,?>> entry : allComponents.entrySet()) {
            String key = entry.getKey();
            Component<?,?> component = entry.getValue();
            
            Object value;
            try {
                value = json.get(key);
            } catch (JSONException ex) {
                if (component.isValue() && component.isRequired()) {
                    errorPresent = true;
                    ResponseValue response = new ResponseValue(key, null, 
                            component.isSingleton(),
                            FormError.MISSING, key);
                    responseMap.put(key, response);
                }
                continue;
            }
            ResponseValue response = component.validate(value);
            if (response.isErrorPresent()) {
                errorPresent = true;
            }
            responseMap.put(key, response);
        }
        
        return new FormResponse(Metadata.EMPTY, responseMap, errorPresent);
    }
    
    /**
     * Validates the json string returned by formio.
     * 
     * @param errors   list of errors returned (key, error)
     * @param jsonText json text to parse
     * @return the json object (check for errors before accepting it)
     */
    public FormResponse validateJsonFromFormio(String jsonText) {
        JSONObject jsonObject = new JSONObject(jsonText);
        JSONObject data = jsonObject.getJSONObject("data");
        Metadata metadata = new Metadata(jsonObject);
        FormResponse response = validateJson(data);
        return response.withMetadata(metadata);
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
