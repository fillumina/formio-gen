package com.fillumina.formio.gen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
// TODO insert data management containers (datagrid and datamap)
public class FormBuilder {

    private final String id;
    private final JSONObject json;
    private final Map<String, Component<?,?>> components;

    /**
     * @see https://github.com/formio/formio.js/wiki/Form-JSON-Schema
     * @param name
     * @param title
     */
    public FormBuilder(String name, String title, String id) {
        this.id = id;
        this.json = new JSONObject();
        json.put("title", title);
        json.put("type", "form");
        json.put("_id", id);
        json.put("display", "form");
        components = new LinkedHashMap<>();
    }

    public FormBuilder addComponent(Component<?,?>... componentArray) {
        for (Component<?,?> component : componentArray) {
            final String key = component.getKey();
            components.put(key, component);
        }
        return this;
    }

    public Form build() {
        final JSONObject jsonObject = toFormioJSONObject();
        Map<String, Component<?, ?>> allComponents = new LinkedHashMap<>();
        for (Component c : components.values()) {
            if (c.isValue()) {
                allComponents.put(c.getKey(), c);
            }
            if (c instanceof AbstractNonValueComponent) {
                ((AbstractNonValueComponent)c).addComponentsToMap(allComponents);
            }
        }
        return new Form(id, jsonObject, allComponents);
    }

    /**
     * @return a json object that can be used by formio to create the form.
     */
    private JSONObject toFormioJSONObject() {
        List<JSONObject> list = components.values().stream()
                .map(c -> c.toJSONObject())
                .collect(Collectors.toList());
        JSONArray array = new JSONArray();
        list.stream().forEach(j -> array.put(j));
        json.put("components", array);
        return json;
    }

}
