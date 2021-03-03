package com.fillumina.formio.gen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

/**
 * @see https://github.com/formio/formio.js/wiki/DateTime-Component
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DateTimeComponent extends Component<DateTimeComponent> {
    public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssX";
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm a";

    public static enum DateType {
        day, year, week, month
    }

    private String format = DEFAULT_FORMAT;
    private Date minDate;
    private Date maxDate;

    public DateTimeComponent(String key) {
        super("datetime", key);
    }

    //https://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date/60214805#60214805
    public Date convert(String s) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(tz);
        Date date = df.parse(s);
        return date;
    }
    
    @Override
    protected ComponentValue innerValidate(List<Object> list) {
        if (list != null) {
            for (Object o : list) {
                if (o != null) {
                    String s = Objects.toString(o);
                    Date date;
                    try {
                        date = convert(s);
                    } catch (ParseException ex) {
                        return new ComponentValue(getKey(), list,
                                FormError.DATE_FORMAT);
                    }
                    if (minDate != null && date.before(minDate)) {
                        return new ComponentValue(getKey(), list,
                                FormError.DATE_BEFORE_MIN);
                    }
                    if (maxDate != null && date.after(maxDate)) {
                        return new ComponentValue(getKey(), list,
                                FormError.DATE_AFTER_MAX);
                    }
                }
            }
        }
        return super.innerValidate(list);
    }

    /**
     * required
     */
    public DateTimeComponent format(String format) {
        this.format = format;
        json.put("format", format);
        return this;
    }

    public DateTimeComponent enableDate(boolean enableDate) {
        json.put("enableDate", enableDate);
        return this;
    }

    public DateTimeComponent enableTime(boolean enableTime) {
        json.put("enableTime", enableTime);
        return this;
    }

    public DateTimeComponent defaultDate(String defaultDate) {
        json.put("defaultDate", defaultDate);
        return this;
    }

    /**
     * required
     */
    public DateTimeComponent datePicker(boolean datePicker) {
        json.put("datePicker", datePicker);
        return this;
    }

    /**
     * required
     */
    public DateTimeComponent timePicker(boolean timePicker) {
        json.put("timePicker", timePicker);
        return this;
    }

    // date picker
    public DateTimeComponent showWeeks(boolean showWeeks) {
        json.put("showWeeks", showWeeks);
        return this;
    }

    public DateTimeComponent startingDay(int startingDay) {
        json.put("startingDay", startingDay);
        return this;
    }

    /**
     * required
     */
    public DateTimeComponent minMode(DateType minMode) {
        json.put("minMode", minMode.name());
        return this;
    }

    /**
     * required
     */
    public DateTimeComponent maxMode(DateType maxMode) {
        json.put("maxMode", maxMode.name());
        return this;
    }

    public DateTimeComponent yearRows(int yearRows) {
        json.put("yearRows", yearRows);
        return this;
    }

    public DateTimeComponent yearColumns(int yearColumns) {
        json.put("yearColumns", yearColumns);
        return this;
    }

    public DateTimeComponent minDate(Date minDate) {
        this.minDate = minDate;
        json.put("minDate", minDate);
        return this;
    }

    public DateTimeComponent maxDate(Date maxDate) {
        this.maxDate = maxDate;
        json.put("maxDate", maxDate);
        return this;
    }

    // time picker
    /**
     * required
     */
    public DateTimeComponent hourStep(int hourStep) {
        json.put("hourStep", hourStep);
        return this;
    }

    /**
     * required
     */
    public DateTimeComponent minuteStep(int minuteStep) {
        json.put("minuteStep", minuteStep);
        return this;
    }

    public DateTimeComponent showMeridian(boolean showMeridian) {
        json.put("showMeridian", showMeridian);
        return this;
    }

    public DateTimeComponent mousewheel(boolean mousewheel) {
        json.put("mousewheel", mousewheel);
        return this;
    }

    public DateTimeComponent readonlyInput(boolean readonlyInput) {
        json.put("readonlyInput", readonlyInput);
        return this;
    }

    public DateTimeComponent arrowkeys(boolean arrowkeys) {
        json.put("arrowkeys", arrowkeys);
        return this;
    }

}
