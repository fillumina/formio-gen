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
public class Panel extends Component<Panel> {
    
    public static enum Theme {
        _default, primary, success, info, warning, danger
    }
    
    private final List<Component<?>> components;
    
    public Panel(String key) {
        super("panel", key); // checkout panel also
        components = new ArrayList<>();
    }
    
    public Panel theme(Theme theme) {
        json.put("theme", theme.toString().replace("_", ""));
        return this;
    }
    
    public Panel theme(String theme) {
        json.put("theme", theme);
        return this;
    }
    
    public Panel title(String title) {
        json.put("title", title);
        return this;
    }
    
    public Panel addComponent(Component<?> component) {
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
