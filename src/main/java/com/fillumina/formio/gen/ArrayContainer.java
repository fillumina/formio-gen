package com.fillumina.formio.gen;

import org.json.JSONArray;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 * @param <T>
 */
public abstract class ArrayContainer<T extends ArrayContainer<T>> extends Container<T> {

    protected final JSONArray components;

    protected ArrayContainer(String type, String key) {
        super(type, key);
        components = new JSONArray();
        json.put("components", components);
    }

    public T addComponent(Component<?,?>... componentArray) {
        for (Component<?,?> component : componentArray) {
            final String key = component.getKey();
            components.put(component.toJSONObject());
            addValidatingComponent(component);
        }
        return (T) this;
    }
}
