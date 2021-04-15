package com.fillumina.formio.gen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DataGridContainer extends ArrayContainer<DataGridContainer> {

    private final Map<String, Component<?, ?>> componentMap = new LinkedHashMap<>();

    public DataGridContainer(String key) {
        super("datagrid", key);
        json.put("addAnotherPosition", "bottom");
        json.put("tableView", true);
        json.put("input", true);
        json.put("tree", true);
    }

    @Override
    public DataGridContainer addComponent(Component<?, ?>... componentArray) {
        for (Component<?,?> component : componentArray) {
            final String key = component.getKey();
            componentMap.put(key, component);
        }
        return super.addComponent(componentArray);
    }

    @Override
    protected void addComponentsToMap(Map<String, Component<?, ?>> allComponents) {
        // stop validation here, manages validation of sub components by itself
        allComponents.put(getKey(), this);
    }

    @Override
    public ResponseValue validate(Object value) {
        JSONArray array = (JSONArray) value;
        List<FormResponse> list = new ArrayList<>();
        for (Object obj : array) {
            JSONObject json = (JSONObject) obj;
            FormResponse response =
                    JsonResponseValidator.validateJson(componentMap, json);
            list.add(response);
        }
        return new ResponseArray(getKey(), getPath(),
                Collections.emptyList(), false, list);
    }

    public DataGridContainer addAnotherText(String text) {
        json.put("addAnother", text);
        return this;
    }

    public DataGridContainer striped(Boolean striped) {
        if (striped == Boolean.TRUE) {
            json.put("striped", true);
        }
        return this;
    }

    public DataGridContainer bordered(Boolean bordered) {
        if (bordered == Boolean.TRUE) {
            json.put("bordered", true);
        }
        return this;
    }

    public DataGridContainer hover(Boolean hover) {
        if (hover == Boolean.TRUE) {
            json.put("hover", true);
        }
        return this;
    }

    public DataGridContainer condensed(Boolean condensed) {
        if (condensed == Boolean.TRUE) {
            json.put("condensed", true);
        }
        return this;
    }

    @Override
    public DataGridContainer minItems(Integer minItems) {
        if (minItems != null) {
            validate.put("minLength", minItems);
        }
        return this;
    }

    @Override
    public DataGridContainer maxItems(Integer maxItems) {
        if (maxItems != null) {
            validate.put("maxLength", maxItems);
        }
        return this;
    }

}
