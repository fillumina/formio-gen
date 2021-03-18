package com.fillumina.formio.gen;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.json.JSONArray;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class ResponseValue {

    private final String key;
    private final String path;
    private final List<?> values;
    private final boolean singleton;
    private final FormError error;
    private final Object[] validationParameters;

    public ResponseValue(String key, String path, List<?> values, boolean singleton) {
        this.key = key;
        this.path = path;
        this.values = values;
        this.singleton = singleton;
        this.error = null;
        this.validationParameters = null;
    }

    public ResponseValue(String key, String path, List<?> values, boolean singleton,
            FormError error, Object... validationParameters) {
        this.key = key;
        this.path = path;
        this.values = values;
        this.singleton = singleton;
        this.error = error;
        this.validationParameters = validationParameters;
    }

    public String getPath() {
        return path != null ? path : key;
    }

    public boolean isErrorPresent() {
        return error != null;
    }

    public boolean isList() {
        return !singleton;
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

    /**
     * @return JSONArray if this is a multiple property or the object {@code value} to be
     *          used into {@code new JSONObject(value)}.
     */
    public Object getJsonObject() {
        if (values == null) {
            return null;
        }
        if (singleton) {
            if (values.isEmpty()) {
                return null;
            }
            return values.get(0);
        }
        return new JSONArray(values);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.key);
        hash = 41 * hash + Objects.hashCode(this.values);
        hash = 41 * hash + (this.singleton ? 1 : 0);
        hash = 41 * hash + Objects.hashCode(this.error);
        hash = 41 * hash + Arrays.deepHashCode(this.validationParameters);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResponseValue other = (ResponseValue) obj;
        if (this.singleton != other.singleton) {
            return false;
        }
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        if (!Objects.equals(this.values, other.values)) {
            return false;
        }
        if (this.error != other.error) {
            return false;
        }
        if (!Arrays.deepEquals(this.validationParameters, other.validationParameters)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return values + (error != null ? " (" + error + ')' : "");
    }
}
