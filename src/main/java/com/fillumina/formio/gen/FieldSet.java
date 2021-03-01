package com.fillumina.formio.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fra
 */
public class FieldSet extends Component<FieldSet> {
    
    private final List<Component<?>> components;
    
    public FieldSet(String key) {
        super("fieldset", key); // checkout panel also
        components = new ArrayList<>();
    }
    
    public FieldSet legend(String legend) {
        json.put("legend", legend);
        return this;
    }
    
    public FieldSet addComponent(Component<?> component) {
        components.add(component);
        return this;
    }

    @Override
    public JSONObject toJSONObject() {
        List<JSONObject> list = components.stream()
                .map(c -> c.toJSONObject())
                .collect(Collectors.toList());
        JSONArray array = new JSONArray();
        list.stream().forEach(j -> array.put(j));
        json.put("components", array);
        return super.toJSONObject();
    }
    
}
