package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TimeComponentTest {

    @Test
    public void shouldDeclareTheTimeTypeAndTheDefaultFormats() {
        TimeComponent comp = new TimeComponent("at123");

        assertEquals("time", comp.toJSONObject().getString("type"));
        assertEquals("HH:mm", comp.toJSONObject().getString("format"));
        assertEquals("HH:mm:ss", comp.toJSONObject().getString("dataFormat"));
    }

    @Test
    public void shouldAcceptTheValueFormioSubmits() {
        TimeComponent comp = new TimeComponent("at123");

        assertFalse(comp.validate("14:30:00").isErrorPresent());
    }

    @Test
    public void shouldRejectAnEmptyValueWhenRequired() {
        TimeComponent comp = new TimeComponent("at123");
        comp.required(true);

        assertEquals(FormError.NULL_VALUE, comp.validate("").getError());
    }

    @Test
    public void shouldKeepTheValueExactlyAsSubmitted() {
        TimeComponent comp = new TimeComponent("at123");

        assertEquals("14:30:00", comp.validate("14:30:00").getValue());
    }
}
