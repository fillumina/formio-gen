package com.fillumina.formio.gen;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public enum FormError {
    NULL_VALUE("null.value"),
    MULTIPLE_VALUES("multiple.value"),
    MULTIPLE_VALUES_TOO_FEW("mutliple.value.min"),
    MULTIPLE_VALUES_TOO_MANY("multiple.value.max"),
    LENGTH_TOO_SHORT("length.min"),
    LENGTH_TOO_LONG("length.max"),
    PATTERN_NOT_MATCHING("pattern.matching"),
    ENUM_ITEM_NOT_PRESENT("enum.item.missing"),
    MIN_VALUE("min.value"),
    MAX_VALUE("max.value"),
    DATE_BEFORE_MIN("date.min"),
    DATE_AFTER_MAX("date.max"),
    PARSE_EXCEPTION("format.error"),
    MISSING("component.value.missing")
    ;

    private final String description;
    
    FormError(String description) {
        this.description = description;
    }
    
    public String getError(Locale locale, Object... values) {
        ResourceBundle resource = ResourceBundle.getBundle("response_error", locale);
        String descr = resource.getString(description);
        int counter = 1;
        for (Object o : values) {
            descr = descr.replace("$" + counter, o.toString());
        }
        return descr;
    }
}
