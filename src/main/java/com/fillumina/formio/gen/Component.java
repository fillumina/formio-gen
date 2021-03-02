package com.fillumina.formio.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class Component<T extends Component<T>> {

    private final String key;
    protected final JSONObject json;
    protected final JSONObject validate;

    private Integer multipleMin;
    private Integer multipleMax;
    private Boolean multiple;
    private Boolean required;
    private Integer minLength;
    private Integer maxLength;
    private Pattern pattern;
    
    public Component(String type, String key) {
        this.key = key;
        this.json = new JSONObject();
        this.validate = new JSONObject();
        json.put("type", type);
        json.put("tableView", false);
        json.put("input", true);
        json.put("key", key);
        json.put("persistent", true); // don't know exactly
        json.put("path", "clothing");
    }

    /**
     * 
     * @param errors adds errors to this
     * @param value might be a single value or an array of values
     */
    public ComponentValue validate(Object value) {
        List<Object> list = toList(value);
        if (required == Boolean.TRUE && (list == null || list.stream().anyMatch(o -> o == null)) ) {
            return new ComponentValue(key, list, FormError.NULL_VALUE);
        }
        if (multiple == Boolean.FALSE && list.size() > 1) {
            return new ComponentValue(key, list, FormError.MULTIPLE_VALUES);
        }
        if (multipleMin != null && list.size() < multipleMin) {
            return new ComponentValue(key, list, FormError.MULTIPLE_VALUES_TOO_FEW);
        }
        if (multipleMax != null && list.size() > multipleMax) {
            return new ComponentValue(key, list, FormError.MULTIPLE_VALUES_TOO_MANY);
        }
        if (minLength != null && list.stream().anyMatch(o -> o.toString().length() < minLength)) {
            return new ComponentValue(key, list, FormError.LENGTH_TOO_SHORT);
        }
        if (maxLength != null && list.stream().anyMatch(o -> o.toString().length() > maxLength)) {
            return new ComponentValue(key, list, FormError.LENGTH_TOO_LONG);
        }
        if (pattern != null && 
                list.stream().anyMatch(o -> !pattern.matcher(o.toString()).matches() ) ) {
            return new ComponentValue(key, list, FormError.PATTERN_NOT_MATCHING);
        }
        return innerValidate(list);
    }
    
    /** 
     * Called <i>after</i> basic validation has been performed. 
     */
    protected ComponentValue innerValidate(List<Object> list) {
        return new ComponentValue(key, list);
    }

    private List<Object> toList(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof JSONArray) {
            List<Object> list = new ArrayList<>();
            for(Object o: (JSONArray) value) {
                list.add(o);
            }            
            return list;
        }
        return List.of(value);
    }
    
    public String getKey() {
        return key;
    }
    
    public JSONObject toJSONObject() {
        addMultipleValidation();
        json.put("validate", validate);
        return json;
    }
    
    public T tabIndex(int tabIndex) {
        json.put("tabindex", tabIndex);
        return (T) this;
    }
    
    public T tableView(boolean tableView) {
        json.put("tableView", tableView);
        return (T) this;
    }
    
    public T theme(Theme theme) {
        json.put("theme", theme.toString());
        return (T) this;
    }
    
    public T theme(String theme) {
        json.put("theme", theme);
        return (T) this;
    }
    
    public T label(String label) {
        json.put("label", label);
        return (T) this;
    }

    public T placeholder(String placeholder) {
        json.put("placeholder", placeholder);
        return (T) this;
    }

    /** Unlimited */
    public T multiple(boolean multiple) {
        this.multiple = multiple;
        json.put("multiple", multiple);
        return (T) this;
    }
    
    public T multipleMin(int multipleMin) {
        this.multipleMin = multipleMin;
        multiple(true);
        return (T) this;
    }
    
    public T multipleMax(int multipleMax) {
        this.multipleMax = multipleMax;
        multiple(true);
        return (T) this;
    }

    public T required(boolean required) {
        this.required = required;
        validate.put("required", required);
        return (T) this;
    }

    public T minLength(int minLength) {
        this.minLength = minLength;
        validate.put("minLength", minLength);
        return (T) this;
    }

    public T maxLength(int maxLength) {
        this.maxLength = maxLength;
        validate.put("maxLength", maxLength);
        return (T) this;
    }

    public T pattern(String pattern) {
        this.pattern = Pattern.compile(pattern);
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
    
    private void addMultipleValidation() {
//        custom("valid = true; console.log('HELLO',component);");
//        if (true) return;
        if (multipleMin != null || multipleMax != null) {
            String variable = "global_data['" + key + "']";
            StringBuilder buf = new StringBuilder();
            buf.append("valid = (");
            buf.append(" Array.isArray(").append(variable).append(") && ");
            if (multipleMin != null) {
                buf.append(variable).append(".length >= ").append(multipleMin);
                if (multipleMax != null) {
                    buf.append( " && ");
                }
            }
            if (multipleMax != null) {
                buf.append(variable).append(".length <= ").append(multipleMax);
            }
            buf.append(" ) ? true : ");
            if (multipleMin != null && multipleMax != null) {
                 buf.append("'there should be between ")
                         .append(multipleMin)
                         .append(" and ")
                         .append(multipleMax)
                         .append("' fields;");
            } else if (multipleMin != null) {
                 buf.append("'there should be at least ")
                         .append(multipleMin)
                         .append(" items';");
            } else {
                 buf.append("'there should be at most ")
                         .append(multipleMax)
                         .append(" items';");
            }
            custom(buf.toString());
        }
        
    }
    
}
