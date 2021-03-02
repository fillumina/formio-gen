package com.fillumina.formio.gen;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class EnumComponent extends Component<EnumComponent> {

    private final JSONObject data;
    private Set<String> validValues;
    
    public EnumComponent(String key) {
        super("select", key);
        json.put("dataSrc", "values");
        data = new JSONObject();
    }

    @Override
    protected ComponentValue innerValidate(List<Object> list) {
        for (Object o : list) {
            String s = Objects.toString(o);
            if (!validValues.contains(s)) {
                return new ComponentValue(getKey(), list, 
                        FormError.ENUM_ITEM_NOT_PRESENT, s);
            }
        }
        return super.innerValidate(list);
    }
    
    public EnumComponent values(String... values) {
        return values(Arrays.asList(values));
    }
    
    public EnumComponent values(Collection<String> values) {
        this.validValues = new HashSet<>(values);
        JSONArray dataValues = new JSONArray();
        values.forEach(v -> dataValues.put(create(v, v)));
        data.put("values", dataValues);
        return this;
    }
    
    public EnumComponent values(Map<String, String> values) {
        this.validValues = new HashSet<>(values.keySet());
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
