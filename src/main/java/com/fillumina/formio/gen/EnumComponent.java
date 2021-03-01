package com.fillumina.formio.gen;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fra
 */
public class EnumComponent extends Component<EnumComponent> {

    private final JSONObject data;
    
    public EnumComponent(String key) {
        super("select", key);
        json.put("dataSrc", "values");
        data = new JSONObject();
    }
    
    public EnumComponent values(String... values) {
        return values(Arrays.asList(values));
    }
    
    public EnumComponent values(Collection<String> values) {
        JSONArray dataValues = new JSONArray();
        values.forEach(v -> dataValues.put(create(v, v)));
        data.put("values", dataValues);
        return this;
    }
    
    public EnumComponent values(Map<String, String> values) {
        JSONArray dataValues = new JSONArray();
        values.forEach((k,v) -> dataValues.put(create(k, v)));
        data.put("values", dataValues);
        return this;
    }
    
    private JSONObject create(String label, String value) {
        JSONObject entry = new JSONObject();
        entry.put("label", label);
        entry.put("value", value);
        return entry;
    }

    @Override
    public JSONObject toJSONObject() {
        json.put("data", data);
        return super.toJSONObject();
    }
}
