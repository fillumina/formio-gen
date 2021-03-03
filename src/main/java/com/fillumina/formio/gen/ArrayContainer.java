package com.fillumina.formio.gen;

import org.json.JSONArray;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ArrayContainer<T extends ArrayContainer<T>> extends Container<T> {

    private final JSONArray components;
    
    protected ArrayContainer(String type, String key) {
        super(type, key);
        components = new JSONArray();
        json.put("components", components);
    }
    
    public T addComponent(Component<?,?> component) {
        components.put(component.toJSONObject());
        addValidatingComponent(component);
        return (T) this;
    }
}
