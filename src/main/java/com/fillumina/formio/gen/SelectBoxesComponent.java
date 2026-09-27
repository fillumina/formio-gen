package com.fillumina.formio.gen;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A list of checkboxes the user can tick any number of.
 *
 * <p>The submitted value is an object mapping each declared value to whether
 * it was ticked, for example
 * <pre>{"red": true, "blue": false}</pre>
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SelectBoxesComponent extends Component<SelectBoxesComponent, JSONObject> {

    private final JSONObject data = new JSONObject();
    private Set<String> validValues;

    public SelectBoxesComponent(String key) {
        super("selectboxes", key);
        json.put("dataSrc", "values");
    }

    public SelectBoxesComponent values(String... values) {
        return values(Arrays.asList(values));
    }

    public SelectBoxesComponent values(Collection<String> values) {
        this.validValues = new HashSet<>(values);
        JSONArray dataValues = new JSONArray();
        values.forEach(v -> dataValues.put(create(v, v)));
        data.put("values", dataValues);
        return this;
    }

    public SelectBoxesComponent values(Map<String, String> values) {
        this.validValues = new HashSet<>(values.values());
        JSONArray dataValues = new JSONArray();
        values.forEach((label, value) -> dataValues.put(create(label, value)));
        data.put("values", dataValues);
        return this;
    }

    private JSONObject create(String label, String value) {
        JSONObject entry = new JSONObject();
        entry.put("label", label);
        entry.put("value", value);
        return entry;
    }

    /** Lays the checkboxes out on one line instead of stacking them. */
    public SelectBoxesComponent inline(Boolean inline) {
        if (inline == Boolean.TRUE) {
            json.put("inline", true);
        } else {
            json.remove("inline");
        }
        return this;
    }

    @Override
    public JSONObject convert(Object obj) throws ParseException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        if (obj instanceof String) {
            String text = ((String) obj).trim();
            if (text.isEmpty()) {
                return null;
            }
            try {
                return new JSONObject(text);
            } catch (JSONException e) {
                throw new ParseException(e.getMessage(), 0);
            }
        }
        throw new ParseException(obj.toString(), 0);
    }

    @Override
    protected ResponseValue innerValidate(List<JSONObject> list) {
        if (list != null) {
            for (JSONObject boxes : list) {
                if (boxes == null) {
                    continue;
                }
                boolean oneTicked = false;
                for (String value : boxes.keySet()) {
                    if (value.isEmpty()) {
                        // formio submits an empty key when nothing is ticked
                        continue;
                    }
                    if (validValues != null && !validValues.contains(value)) {
                        return new ResponseValue(getKey(), getPath(), list, isSingleton(),
                                FormError.ENUM_ITEM_NOT_PRESENT, value);
                    }
                    Object ticked = boxes.get(value);
                    if (!(ticked instanceof Boolean)) {
                        return new ResponseValue(getKey(), getPath(), list, isSingleton(),
                                FormError.PARSE_EXCEPTION, value + "=" + ticked);
                    }
                    oneTicked = oneTicked || (Boolean) ticked;
                }
                if (isRequired() && !oneTicked) {
                    return new ResponseValue(getKey(), getPath(), list, isSingleton(),
                            FormError.NULL_VALUE);
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
