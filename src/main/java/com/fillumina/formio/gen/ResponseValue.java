package com.fillumina.formio.gen;

import java.util.List;
import java.util.Locale;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class ResponseValue {
    
    private final String key;
    private final List<?> values;
    private final FormError error;
    private final Object[] validationParameters;

    public ResponseValue(String key, Object value) {
        this.key = key;
        this.values = List.of(value);
        this.error = null;
        this.validationParameters = null;
    }

    public ResponseValue(String key, List<?> values) {
        this.key = key;
        this.values = values;
        this.error = null;
        this.validationParameters = null;
    }

    public ResponseValue(String key, List<?> values, FormError error, 
            Object... validationParameters) {
        this.key = key;
        this.values = values;
        this.error = error;
        this.validationParameters = validationParameters;
    }
    
    public boolean isErrorPresent() {
        return error != null;
    }

    public String getKey() {
        return key;
    }

    public List<?> getValues() {
        return values;
    }

    public FormError getError() {
        return error;
    }

    public String getErrorDescription(Locale locale) {
        return error.getError(locale, validationParameters);
    }

    @Override
    public String toString() {
        return values + (error != null ? " (" + error + ')' : "");
    }
}
