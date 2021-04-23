package com.fillumina.formio.gen;

import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class JsonResponseValidator {

    /**
     * Validates a json string against the rules specified in this form.
     */
    public static FormResponse validateJson(
            Map<String, Component<?, ?>> components,
            String jsonText) {
        JSONObject json = new JSONObject(jsonText);
        return validateJson(components, json);
    }

    /**
     * Validates a {@link JSONObject} against the rules specified in this form.
     */
    public static FormResponse validateJson(
            Map<String, Component<?, ?>> components,
            JSONObject json) {
        boolean errorPresent = false;
        Map<String, ResponseValue> responseMap = new LinkedHashMap<>();

        for (Map.Entry<String, Component<?, ?>> entry : components.entrySet()) {
            String key = entry.getKey();
            Component<?, ?> component = entry.getValue();

            Object value;
            try {
                value = json.get(key);
            } catch (JSONException ex) {
                if (component.isValue() && component.isRequired()) {
                    errorPresent = true;
                    ResponseValue response = new ResponseValue(key, null, null,
                            component.isSingleton(),
                            FormError.MISSING, key);
                    responseMap.put(key, response);
                }
                continue;
            }

            ResponseValue responseValue = component.validate(value);
            errorPresent = errorPresent || responseValue.isErrorPresent();
            responseMap.put(responseValue.getKey(), responseValue);
        }

        return new FormResponse(Metadata.EMPTY, responseMap, errorPresent);
    }

}
