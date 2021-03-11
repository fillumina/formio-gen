package com.fillumina.formio.gen;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractNonValueComponent<T extends AbstractNonValueComponent<T>>
        extends Component<T,Void> {

    private final Map<String, Component<?,?>> components;

    public AbstractNonValueComponent(String type, String key) {
        super(type, key);
        components = new LinkedHashMap<>();
    }

    @Override
    protected boolean isValue() {
        return false;
    }

    @Override
    public Void convert(Object s) throws ParseException {
        return null;
    }

    /** @return a flat map of all registered components for validation. */
    protected void addComponentsToMap(Map<String, Component<?,?>> allComponents) {
        components.values().forEach(c -> {
            if (c instanceof AbstractNonValueComponent) {
                ((AbstractNonValueComponent)c).addComponentsToMap(allComponents);
            } else if (c.isValue()) {
                allComponents.put(c.getKey(), c);
            }
        });
    }

    protected T addValidatingComponent(Component<?,?> component) {
        components.put(component.getKey(), component);
        return (T) this;
    }

}
