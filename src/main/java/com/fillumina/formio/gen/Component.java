package com.fillumina.formio.gen;

import org.json.JSONObject;

/**
 *
 * @author fra
 */
public abstract class Component<T extends Component<T>> {

    protected final JSONObject json;
    protected final JSONObject validate;

    public Component(String type, String key) {
        json = new JSONObject();
        validate = new JSONObject();
        json.put("type", type);
        json.put("tableView", false);
        json.put("input", true);
        json.put("key", key);
    }

    public JSONObject toJSONObject() {
        json.put("validate", validate);
        return json;
    }

    public T label(String label) {
        json.put("label", label);
        return (T) this;
    }

    public T placeholder(String placeholder) {
        json.put("placeholder", placeholder);
        return (T) this;
    }

    public T multiple(boolean multiple) {
        json.put("multiple", multiple);
        return (T) this;
    }

    public T required(boolean required) {
        validate.put("required", required);
        return (T) this;
    }

    public T minLength(int minLength) {
        validate.put("minLength", minLength);
        return (T) this;
    }

    public T maxLength(int maxLength) {
        validate.put("maxLength", maxLength);
        return (T) this;
    }

    public T pattern(String pattern) {
        validate.put("pattern", pattern);
        return (T) this;
    }

    /**
     * You must assign the valid variable as either true or an error message if validation fails.
     * The global variables {@code input}, {@code component}, and {@code valid} are provided.
     * <p>
     * Example:
     * <pre>
     * valid = (input === 3) ? true : 'Must be 3';
     * </pre>
     *
     * @param custom
     * @return
     */
    public T custom(String custom) {
        validate.put("custom", custom);
        return (T) this;
    }
}
