package com.fillumina.formio.gen;

import java.util.Collections;
import java.util.Map;
import org.json.JSONArray;
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
public class Form {

    private final String id;
    private final JSONObject json;
    private final Map<String, Component<?, ?>> allComponents;

    public Form(String id, JSONObject json,
            Map<String, Component<?, ?>> allComponents) {
        this.id = id;
        this.json = JSONUtils.clone(json);
        this.allComponents = Collections.unmodifiableMap(allComponents);
    }

    /**
     * Validates a json string against the rules specified in this form.
     */
    public FormResponse validateJson(String jsonText) {
        return JsonResponseValidator.validateJson(allComponents, json);
    }

    /**
     * Validates a {@link JSONObject} against the rules specified in this form.
     */
    public FormResponse validateJson(JSONObject json) {
        return JsonResponseValidator.validateJson(allComponents, json);
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
        FormResponse response = JsonResponseValidator.validateJson(allComponents, data);
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
        // defensive cloning
        JSONObject data = JSONUtils.clone(json);
        if (values == null || values.isEmpty()) {
            return data;
        }
        final JSONArray array = data.getJSONArray("components");
        JSONUtils.setValuesToProperty(array, values, "defaultValue");
        return data;
    }

}
