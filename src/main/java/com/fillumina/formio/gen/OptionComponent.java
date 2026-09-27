package com.fillumina.formio.gen;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A component the user picks a single value from, such as a dropdown or a
 * radio group. Not to be used directly.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class OptionComponent<T extends OptionComponent<T>> extends StringComponent<T> {

    private final JSONObject data = new JSONObject();
    private Set<String> validValues;

    protected OptionComponent(String type, String key) {
        super(type, key);
        json.put("dataSrc", "values");
    }

    public T values(String... values) {
        return values(Arrays.asList(values));
    }

    public T values(Collection<String> values) {
        this.validValues = new HashSet<>(values);
        JSONArray dataValues = new JSONArray();
        values.forEach(v -> dataValues.put(create(v, v)));
        data.put("values", dataValues);
        return (T) this;
    }

    public T values(Map<String, String> values) {
        this.validValues = new HashSet<>(values.values());
        JSONArray dataValues = new JSONArray();
        values.forEach((label, value) -> dataValues.put(create(label, value)));
        data.put("values", dataValues);
        return (T) this;
    }

    private JSONObject create(String label, String value) {
        JSONObject entry = new JSONObject();
        entry.put("label", label);
        entry.put("value", value);
        return entry;
    }

    @Override
    protected ResponseValue innerValidate(List<String> list) {
        if (list != null && validValues != null) {
            for (String s : list) {
                if (!validValues.contains(s)) {
                    return new ResponseValue(getKey(), getPath(), list, isSingleton(),
                            FormError.ENUM_ITEM_NOT_PRESENT, s);
                }
            }
        }
        return super.innerValidate(list);
    }

    @Override
    public JSONObject toJSONObject() {
        json.put("data", data);
        return super.toJSONObject();
    }
}
