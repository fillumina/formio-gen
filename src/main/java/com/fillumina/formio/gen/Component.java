package com.fillumina.formio.gen;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class Component<T extends Component<T,V>,V> {

    private final String key;
    protected final JSONObject json;
    protected final JSONObject validate;
    private Function<V,Object> externalValidator;

    private Integer minItems;
    private Integer maxItems;
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
        multiple(false);
    }

    /** The object might be already in the right format or should convert from {@code String}. */
    public abstract V convert(Object s) throws ParseException;
    
    protected boolean isRequired() {
        return required == Boolean.TRUE;
    }
    
    /** Containers don't return values. */
    protected boolean isValue() {
        return true;
    }
    
    public T externalValidator(Function<V,Object> validator) {
        this.externalValidator = validator;
        return (T) this;
    }
    
    /** @return true if doesn't accept multiple values. */
    public boolean isSingleton() {
        return multiple == Boolean.FALSE || multiple == null || 
                (maxItems != null && maxItems == 1);
    }
    
    /**
     * 
     * @param value can be a java object (like BigDecimal or String) or a JSONArray
     * @return 
     */
    public ResponseValue validate(Object value) {
        List<V> list;
        final boolean singleton = isSingleton();
        try {
            list = toList(value);
        } catch (ParseException e) {
            return new ResponseValue(key, List.of(e.getMessage()), singleton,
                    FormError.PARSE_EXCEPTION);
        }
        if ((required == Boolean.TRUE || (minLength != null && minLength > 0)) && 
                (list == null || list.stream().anyMatch(o -> o == null)) ) {
            return new ResponseValue(key, list, singleton, FormError.NULL_VALUE);
        }
        if (list != null) {
            if (multiple == Boolean.FALSE && list.size() > 1) {
                return new ResponseValue(key, list, singleton, FormError.MULTIPLE_VALUES);
            }
            if (minItems != null && list.size() < minItems) {
                return new ResponseValue(key, list, singleton, FormError.MULTIPLE_VALUES_TOO_FEW);
            }
            if (maxItems != null && list.size() > maxItems) {
                return new ResponseValue(key, list, singleton, FormError.MULTIPLE_VALUES_TOO_MANY);
            }
            if (minLength != null && list.stream().anyMatch(o -> o.toString().length() < minLength)) {
                return new ResponseValue(key, list, singleton, FormError.LENGTH_TOO_SHORT);
            }
            if (maxLength != null && list.stream().anyMatch(o -> o.toString().length() > maxLength)) {
                return new ResponseValue(key, list, singleton, FormError.LENGTH_TOO_LONG);
            }
            if (pattern != null && 
                    list.stream().anyMatch(o -> !pattern.matcher(o.toString()).matches() ) ) {
                return new ResponseValue(key, list, singleton, FormError.PATTERN_NOT_MATCHING);
            }
            if (externalValidator != null) {
                for (V v : list) {
                    Object error = externalValidator.apply(v);
                    if (error != null) {
                        return new ResponseValue(key, list, singleton,
                                FormError.EXTERNAL_VALIDATOR, error.toString());
                    }
                }
            }
        }
        return innerValidate(list);
    }
    
    /** 
     * Called <i>after</i> basic validation has been performed.
     * Overwrite this method to do further validations and/or call super to report no error result.
     */
    protected ResponseValue innerValidate(List<V> list) {
        // return no error result
        return new ResponseValue(key, list, isSingleton());
    }

    private List<V> toList(Object value) throws ParseException {
        if (value == null) {
            return null;
        }
        if (value instanceof JSONArray) {
            List<V> list = new ArrayList<>();
            for(Object o: (JSONArray) value) {
                if (o == null) {
                    list.add(null);
                } else {
                    list.add(convert(o));
                }
            }            
            return list;
        }
        V convert = convert(value);
        return convert == null ? null : List.of(convert);
    }
    
    public String getKey() {
        return key;
    }
    
    public JSONObject toJSONObject() {
        addMultipleValidation();
        json.put("validate", validate);
        return json;
    }
    
    public T defaultValue(JSONObject jsonObject) {
        json.put("defaultValue", jsonObject);
        return (T) this;
    }

    public T defaultValueList(List<Object> defaultValueList) {
        JSONArray array = new JSONArray(defaultValueList);
        json.put("defaultValue", array);
        return (T) this;
    }

    /** Use to add undescribed or not available options to the Component. */
    public T addOption(String name, Object value) {
        json.put(name, value);
        return (T) this;
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

    public T tooltip(String tooltip) {
        json.put("tooltip", tooltip);
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
    
    public T minItems(int minItems) {
        this.minItems = minItems;
        validate.put("minItems", minItems);
        multiple(true);
        return (T) this;
    }
    
    public T maxItems(int maxItems) {
        this.maxItems = maxItems;
        validate.put("maxItems", maxItems);
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

    public T description(String description) {
        validate.put("description", description);
        return (T) this;
    }

    public T maxLength(int maxLength) {
        this.maxLength = maxLength;
        validate.put("maxLength", maxLength);
        return (T) this;
    }

    public T pattern(Pattern pattern) {
        this.pattern = pattern;
        validate.put("pattern", pattern);
        return (T) this;
    }

    public T pattern(String pattern) {
        this.pattern = Pattern.compile(pattern);
        validate.put("pattern", pattern);
        return (T) this;
    }
    
    public T validateOnBlur() {
        json.put("validateOn", "blur");
        return (T) this;
    }
    
    public T validateOnChange() {
        json.remove("validateOn");
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
     * <a href='https://github.com/formio/formio.js/issues/1867'>
     * Provided variables:
     * </a>
     * <table>
     * <tr><th>input</th><td>the input value into this component</td></tr>
     * <tr><th>form</th><td>the complete form JSON object</td></tr>
     * <tr><th>submission</th><td>the complete submission object</td></tr>
     * <tr><th>data</th><td>the complete submission data object</td></tr>
     * <tr><th>row</th><td>contextual 'row' data, used within DataGrid and containers</td></tr>
     * <tr><th>component</th><td>the current component JSON</td></tr>
     * <tr><th>instance</th><td>the current component instance</td></tr>
     * <tr><th>value</th><td>the current value of the component</td></tr>
     * <tr><th>moment</th><td>the moment.js library for date manipulation</td></tr>
     * <tr><th>_</th><td>an instance of Lodash</td></tr>
     * <tr><th>utils</th><td>instance of FormioUtils object</td></tr>
     * <tr><th>util</th><td>alias for utils</td></tr>
     * </table>
     *
     * @param custom
     * @return
     */
    public T custom(String custom) {
        validate.put("custom", custom);
        return (T) this;
    }
    
    private void addMultipleValidation() {
        final boolean minItemsNotNull = minItems != null;
        final boolean maxItemsNotNull = maxItems != null;
        if (minItemsNotNull || maxItemsNotNull) {
            String variable = "data['" + key + "']";
            StringBuilder buf = new StringBuilder();
            buf.append("valid = (");
            buf.append(" Array.isArray(").append(variable).append(") && ");
            if (minItemsNotNull) {
                buf.append(variable).append(".length >= ").append(minItems);
                if (maxItemsNotNull) {
                    buf.append( " && ");
                }
            }
            if (maxItemsNotNull) {
                buf.append(variable).append(".length <= ").append(maxItems);
            }
            buf.append(" ) ? true : ");
            buf.append("'multiplicity';");
            custom(buf.toString());
        }
    }
    
    public Component<?,?>[] createMultipleInfoComponent() {
        if (minItems == null && maxItems == null) {
            return new Component[]{this};
        } else {
            StringBuilder buf = new StringBuilder();
            buf.append("<span style=\"color: grey;font-size: small;\">[");
            if (minItems != null) {
                buf.append("min items=").append(minItems);
                if (maxItems != null) {
                    buf.append(", ");
                }
            }
            if (maxItems != null) {
                buf.append("max items=").append(maxItems);
            }
            buf.append("]</span>");
            Component<?,?> info = new HtmlComponent(key+"_info").html(buf.toString());
            return new Component[]{this, info};
        }
    }
}
