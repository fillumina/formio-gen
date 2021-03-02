package com.fillumina.formio.gen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class Container<T extends Container<T>> extends Component<T> {
    
    private final Map<String, Component<?>> components;

    public Container(String type, String key) {
        super(type, key);
        components = new LinkedHashMap<>();
    }

    void addComponents(Map<String, Component<?>> allComponents) {
        components.values().forEach(c -> {
            if (c instanceof Container) {
                ((Container)c).addComponents(allComponents);
            } else {
                allComponents.put(c.getKey(), c);
            }
        });
    }
    
    public T addComponent(Component<?> component) {
        components.put(component.getKey(), component);
        return (T) this;
    }

    @Override
    public JSONObject toJSONObject() {
        List<JSONObject> list = components.values().stream()
                .map(c -> c.toJSONObject())
                .collect(Collectors.toList());
        JSONArray array = new JSONArray();
        list.stream().forEach(j -> array.put(j));
        json.put("components", array);
        return super.toJSONObject();
    }
    
}
