package com.fillumina.formio.gen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A container holding a repeated set of components, such as a table or an
 * editable list. Every entry is validated as a form of its own, so the
 * sub components are not registered with the parent form. Not to be used
 * directly.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class SubFormArrayContainer<T extends SubFormArrayContainer<T>>
        extends ArrayContainer<T> {

    private final Map<String, Component<?, ?>> componentMap = new LinkedHashMap<>();

    protected SubFormArrayContainer(String type, String key) {
        super(type, key);
    }

    @Override
    public T addComponent(Component<?, ?>... componentArray) {
        for (Component<?, ?> component : componentArray) {
            componentMap.put(component.getKey(), component);
        }
        return super.addComponent(componentArray);
    }

    @Override
    protected void addComponentsToMap(Map<String, Component<?, ?>> allComponents) {
        allComponents.put(getKey(), this);
    }

    /**
     * The rows are what the user filled in, so a required section is expected
     * in the submission even though the section itself returns no value.
     */
    @Override
    public boolean isExpectedInSubmission() {
        return isRequired();
    }

    /**
     * Validates each entry as a form of its own. A value that is absent, null
     * or not an array is an empty list of entries, which is an error only when
     * the section is required.
     */
    @Override
    public ResponseValue validate(Object value) {
        List<FormResponse> list = new ArrayList<>();
        if (value instanceof JSONArray) {
            for (Object obj : (JSONArray) value) {
                if (obj instanceof JSONObject) {
                    list.add(JsonResponseValidator.validateJson(componentMap, (JSONObject) obj));
                }
            }
        }
        if (isRequired() && list.isEmpty()) {
            return new ResponseArray(getKey(), getPath(), Collections.emptyList(), false,
                    FormError.NULL_VALUE, list);
        }
        return new ResponseArray(getKey(), getPath(), Collections.emptyList(), false, list);
    }

    protected Map<String, Component<?, ?>> getComponentMap() {
        return Collections.unmodifiableMap(componentMap);
    }
}
