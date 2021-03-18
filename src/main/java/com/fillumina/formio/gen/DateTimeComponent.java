package com.fillumina.formio.gen;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @see https://github.com/formio/formio.js/wiki/DateTime-Component
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DateTimeComponent extends Component<DateTimeComponent, XMLGregorianCalendar> {

    public static enum DateType {
        day, year, week, month
    }

    private Date minDate;
    private Date maxDate;

    public DateTimeComponent(String key) {
        super("datetime", key);
    }

    //https://stackoverflow.com/a/60214805/203204
    @Override
    public XMLGregorianCalendar convert(Object obj) throws ParseException {
        if (obj == null || obj.toString().isBlank()) {
            return null;
        }
        final String str = obj.toString();
        try {
            return toXmlGregorianCalendar(str);
        } catch (DatatypeConfigurationException | IllegalArgumentException ex) {
            throw new ParseException(str, 0);
        }
    }

    private XMLGregorianCalendar toXmlGregorianCalendar(final Object obj)
            throws DatatypeConfigurationException, IllegalArgumentException {
        XMLGregorianCalendar xmlGregorianCalendar =
                DatatypeFactory.newInstance().newXMLGregorianCalendar(obj.toString());
        return xmlGregorianCalendar;
    }

    private Date toDate(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().getTime();
    }

    @Override
    protected ResponseValue innerValidate(List<XMLGregorianCalendar> list) {
        if (list != null) {
            for (XMLGregorianCalendar cal : list) {
                Date date = toDate(cal);
                if (date != null) {
                    if (minDate != null && date.before(minDate)) {
                        return new ResponseValue(getKey(),  getPath(), list, isSingleton(),
                                FormError.DATE_BEFORE_MIN);
                    }
                    if (maxDate != null && date.after(maxDate)) {
                        return new ResponseValue(getKey(),  getPath(), list, isSingleton(),
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
