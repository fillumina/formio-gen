package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina at gmail.com>
 */
public class BooleanComponentTest {
        
    @Test
    public void shouldRejectWrongBoolean() {
        BooleanComponent comp = new BooleanComponent("txt123");
        ComponentValue cv = comp.validate("yes");
        assertEquals(FormError.BOOLEAN_NOT_FOUND, cv.getError());
        assertTrue(cv.isErrorPresent());
    }
        
    @Test
    public void shouldAcceptTrue() {
        BooleanComponent comp = new BooleanComponent("txt123");
        ComponentValue cv = comp.validate("true");
        assertFalse(cv.isErrorPresent());
    }
        
    @Test
    public void shouldAcceptFunnyFalse() {
        BooleanComponent comp = new BooleanComponent("txt123");
        ComponentValue cv = comp.validate("FaLse");
        assertFalse(cv.isErrorPresent());
    }
}
