package com.fillumina.formio.gen;

import java.util.List;
import java.util.Locale;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class ComponentValue {
    
    private final String key;
    private final List<Object> values;
    private final FormError error;
    private final Object[] validation;

    public ComponentValue(String key, Object value) {
        this.key = key;
        this.values = List.of(value);
        this.error = null;
        this.validation = null;
    }

    public ComponentValue(String key, List<Object> values) {
        this.key = key;
        this.values = values;
        this.error = null;
        this.validation = null;
    }

    public ComponentValue(String key, List<Object> values, FormError error, Object... validation) {
        this.key = key;
        this.values = values;
        this.error = error;
        this.validation = validation;
    }
    
    public boolean isErrorPresent() {
        return error != null;
    }

    public String getKey() {
        return key;
    }

    public List<Object> getValues() {
        return values;
    }

    public FormError getError() {
        return error;
    }

    public String getErrorDescription(Locale locale) {
        return error.getError(locale, validation);
    }

    @Override
    public String toString() {
        return values + (error != null ? " (" + error + ')' : "");
    }
}
