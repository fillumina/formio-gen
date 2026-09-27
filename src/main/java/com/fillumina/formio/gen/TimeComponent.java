package com.fillumina.formio.gen;

/**
 * A time of day. The submitted value is a string in the
 * {@link #dataFormat(String) data format}, {@code HH:mm:ss} unless changed.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TimeComponent extends StringComponent<TimeComponent> {

    public TimeComponent(String key) {
        super("time", key);
        json.put("format", "HH:mm");
        json.put("dataFormat", "HH:mm:ss");
    }

    /** @param format how the time is shown, {@code HH:mm} by default */
    public TimeComponent format(String format) {
        if (format != null) {
            json.put("format", format);
        } else {
            json.remove("format");
        }
        return this;
    }

    /** @param dataFormat the format of the submitted value, {@code HH:mm:ss} by default */
    public TimeComponent dataFormat(String dataFormat) {
        if (dataFormat != null) {
            json.put("dataFormat", dataFormat);
        } else {
            json.remove("dataFormat");
        }
        return this;
    }
}
