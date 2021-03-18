package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class EnumComponentTest {

    @Test
    public void shouldRejectWrongSelection() {
        EnumComponent comp = new EnumComponent("txt123");
        comp.values("one", "two", "three");
        ResponseValue cv = comp.validate("four");
        assertEquals(FormError.ENUM_ITEM_NOT_PRESENT, cv.getError());
        assertTrue(cv.isErrorPresent());
    }

    @Test
    public void shouldAcceptGoodSelection() {
        EnumComponent comp = new EnumComponent("txt123");
        comp.values("one", "two", "three");
        ResponseValue cv = comp.validate("two");
        assertFalse(cv.isErrorPresent());
    }
}
