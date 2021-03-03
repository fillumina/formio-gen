package com.fillumina.formio.gen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DateTimeComponentTest {

    @Test
    public void shouldAcceptISOValue() {
        String nowAsISO = getNowAsISOString(0);

        DateTimeComponent comp = new DateTimeComponent("dt123");
        ComponentValue cv = comp.validate(nowAsISO);
        assertFalse(cv.isErrorPresent());
    }

    @Test
    public void shouldAcceptDefaultValue() throws ParseException {
        DateTimeComponent comp = new DateTimeComponent("dt123");
        ComponentValue cv = comp.validate("2021-03-11T00:00:00+01:00");
        assertFalse(cv.isErrorPresent());
    }

    @Test
    public void shouldRejectBadFormat() {
        DateTimeComponent comp = new DateTimeComponent("dt123");
        ComponentValue cv = comp.validate("12-03-2010");
        assertEquals(FormError.PARSE_EXCEPTION, cv.getError());
        assertTrue(cv.isErrorPresent());
    }

    @Test
    public void shouldRejectBeforeMin() {
        DateTimeComponent comp = new DateTimeComponent("dt123");
        comp.minDate(getNowAsDate(-3));
        ComponentValue cv = comp.validate(getNowAsISOString(-10));
        assertEquals(FormError.DATE_BEFORE_MIN, cv.getError());
        assertTrue(cv.isErrorPresent());
    }

    @Test
    public void shouldRejectAfterMax() {
        DateTimeComponent comp = new DateTimeComponent("dt123");
        comp.maxDate(getNowAsDate(3));
        ComponentValue cv = comp.validate(getNowAsISOString(10));
        assertEquals(FormError.DATE_AFTER_MAX, cv.getError());
        assertTrue(cv.isErrorPresent());
    }

    @Test
    public void shouldAcceptInBetweenDate() {
        DateTimeComponent comp = new DateTimeComponent("dt123");
        comp.minDate(getNowAsDate(-3));
        comp.maxDate(getNowAsDate(3));
        ComponentValue cv = comp.validate(getNowAsISOString(0));
        assertFalse(cv.isErrorPresent());
    }

    private String getNowAsISOString(int days) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        df.setTimeZone(tz);
        Date d = getNowAsDate(days);
        String nowAsISO = df.format(d);
        return nowAsISO;
    }

    private static Date getNowAsDate(int days) {
        return new Date(new Date().getTime() + days * (24 * 60 * 60 * 1000));
    }
    
    private Date convert(String s, String format) throws ParseException {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(tz);
        Date date = df.parse(s);
        return date;
    }
    
}
