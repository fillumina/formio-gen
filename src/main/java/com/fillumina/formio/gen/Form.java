package com.fillumina.formio.gen;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
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
// TODO insert conditional display
public class Form {

    private final String id;
    private final JSONObject json;
    private final Map<String, Component<?, ?>> allComponents;
    private final Map<String, Component<?, ?>> components;

    public Form(String id, JSONObject json,
            Map<String, Component<?, ?>> allComponents,
            Map<String, Component<?, ?>> components) {
        this.id = id;
        this.json = JSONUtils.clone(json);
        this.allComponents = Collections.unmodifiableMap(allComponents);
        this.components = Collections.unmodifiableMap(components);
    }

    /**
     * Validates a json string against the rules specified in this form.
     */
    public FormResponse validateJson(String jsonText) {
        JSONObject json = new JSONObject(jsonText);
        return validateJson(json);
    }

    /**
     * Validates a {@link JSONObject} against the rules specified in this form.
     */
    public FormResponse validateJson(JSONObject json) {
        boolean errorPresent = false;
        Map<String, ResponseValue> responseMap = new LinkedHashMap<>();

        for (Entry<String, Component<?, ?>> entry : allComponents.entrySet()) {
            String key = entry.getKey();
            Component<?, ?> component = entry.getValue();

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
     * @param errors list of errors returned (key, error)
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

    /**
     * @return a json object that can be used by formio to create the form.
     */
    public JSONObject toFormioJSONObject() {
        return toFormioJSONObjectAddingValues(null);
    }

    /**
     * @param values a flat object with key->value
     * @return a json object that can be used by formio to create the form.
     */
    public JSONObject toFormioJSONObjectAddingValues(JSONObject values) {
        // conservative cloning the object
        JSONObject data = JSONUtils.clone(json);
        if (values != null && !values.isEmpty() && json.keySet().contains("components")) {
            final JSONArray array = json.getJSONArray("components");
            iterativeSetValues(array, values);
        }
        return data;
    }

    private void iterativeSetValues(JSONArray array, JSONObject values) {
        for (Object item : array) {
            if (item instanceof JSONObject) {
                JSONObject obj = (JSONObject) item;
                if (obj.keySet().contains("key")) {
                    String objectKey = obj.getString("key");
                    if (objectKey != null && values.keySet().contains(objectKey)) {
                        Object value = values.get(objectKey);
                        if (value != null) {
                            obj.put("defaultValue", value);
                        }
                    }
                }
            } else if (item instanceof JSONArray) {
                JSONArray components = (JSONArray) item;
                iterativeSetValues(components, values);
            }
        }
    }
}
