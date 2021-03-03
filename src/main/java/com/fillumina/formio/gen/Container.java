package com.fillumina.formio.gen;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class Container<T extends Container<T>> extends Component<T,Void> {
    
    private final Map<String, Component<?,?>> components;
    
    public Container(String type, String key) {
        super(type, key);
        components = new LinkedHashMap<>();
    }
    
    @Override
    protected boolean isValue() {
        return false;
    }
    
    @Override
    public Void convert(String s) {
        return null;
    }

    /** @return a flat map of all registered components for validation. */
    protected void addComponentsToMap(Map<String, Component<?,?>> allComponents) {
        components.values().forEach(c -> {
            if (c instanceof Container) {
                ((Container)c).addComponentsToMap(allComponents);
            } else {
                allComponents.put(c.getKey(), c);
            }
        });
    }
    
    protected T addValidatingComponent(Component<?,?> component) {
        components.put(component.getKey(), component);
        return (T) this;
    }
    
}
