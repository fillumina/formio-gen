package com.fillumina.formio.gen;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class Container<T extends Container<T>> extends AbstractNonValueComponent<T> {

    private final Map<String, Component<?,?>> components;

    public Container(String type, String key) {
        super(type, key);
        components = new LinkedHashMap<>();
    }

    /** @return a flat map of all registered components for validation. */
    protected void addComponentsToMap(Map<String, Component<?,?>> allComponents) {
        components.values().forEach(c -> {
            if (c instanceof Container) {
                ((Container)c).addComponentsToMap(allComponents);
            } else if (c.isValue()) {
                allComponents.put(c.getKey(), c);
            }
        });
    }

    protected T addValidatingComponent(Component<?,?> component) {
        components.put(component.getKey(), component);
        return (T) this;
    }

    public Map<String, Component<?, ?>> getComponents() {
        return Collections.unmodifiableMap(components);
    }

    @Override
    public T required(Boolean required) {
        if (required != null) {
            components.values().forEach(c -> c.required(required));
        }
        return (T) this;
    }

}

