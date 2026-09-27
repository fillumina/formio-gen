package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DayComponentTest {

    @Test
    public void shouldDeclareTheDayType() {
        DayComponent comp = new DayComponent("born123");

        assertEquals("day", comp.toJSONObject().getString("type"));
    }

    @Test
    public void shouldReadMonthFirstByDefault() {
        DayComponent comp = new DayComponent("born123");

        PartialDate date = (PartialDate) comp.validate("03/14/2026").getValue();

        assertEquals(2026, date.getYear());
        assertEquals(3, date.getMonth());
        assertEquals(14, date.getDay());
        assertTrue(date.isComplete());
    }

    @Test
    public void shouldReadDayFirstWhenThePropertySaysSo() {
        DayComponent comp = new DayComponent("born123");
        comp.dayFirst(true);

        PartialDate date = (PartialDate) comp.validate("14/03/2026").getValue();

        assertEquals(2026, date.getYear());
        assertEquals(3, date.getMonth());
        assertEquals(14, date.getDay());
    }

    @Test
    public void shouldAcceptADateTheUserOnlyPartlyFilledIn() {
        DayComponent comp = new DayComponent("born123");

        PartialDate date = (PartialDate) comp.validate("03/00/0000").getValue();

        assertEquals(3, date.getMonth());
        assertEquals(0, date.getDay());
        assertFalse(date.isComplete());
    }

    @Test
    public void shouldRejectSomethingThatIsNotADate() {
        DayComponent comp = new DayComponent("born123");

        assertEquals(FormError.PARSE_EXCEPTION, comp.validate("not a date").getError());
        assertEquals(FormError.PARSE_EXCEPTION, comp.validate("03/14").getError());
    }

    @Test
    public void shouldRejectAnImpossibleMonthOrDay() {
        DayComponent comp = new DayComponent("born123");

        assertEquals(FormError.PARSE_EXCEPTION, comp.validate("13/14/2026").getError());
        assertEquals(FormError.PARSE_EXCEPTION, comp.validate("03/32/2026").getError());
    }

    @Test
    public void shouldRejectAnEmptyValueWhenRequired() {
        DayComponent comp = new DayComponent("born123");
        comp.required(true);

        assertEquals(FormError.NULL_VALUE, comp.validate("").getError());
    }

    @Test
    public void shouldWriteTheDateBackInTheOrderItWasRead() {
        DayComponent comp = new DayComponent("born123");
        comp.dayFirst(true);

        PartialDate date = (PartialDate) comp.validate("14/03/2026").getValue();

        assertEquals("14/03/2026", date.format(true));
        assertEquals("03/14/2026", date.format(false));
    }
}
