package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class IntegerComponentTest {
    
    @Test
    public void shouldRejectValue() {
        IntegerComponent comp = new IntegerComponent("int123");
        ComponentValue cv = comp.validate("12.34");
        assertEquals(FormError.PARSE_EXCEPTION, cv.getError());
        assertTrue(cv.isErrorPresent());
    }    
            
    @Test
    public void shouldAcceptValue() {
        IntegerComponent comp = new IntegerComponent("int123");
        ComponentValue cv = comp.validate("1234");
        assertFalse(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldAcceptMinMax() {
        IntegerComponent comp = new IntegerComponent("int123");
        comp.min(10);
        comp.max(20);
        ComponentValue cv = comp.validate("12");
        assertFalse(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldRejectMin() {
        IntegerComponent comp = new IntegerComponent("int123");
        comp.min(10);
        ComponentValue cv = comp.validate("2");
        assertEquals(FormError.MIN_VALUE, cv.getError());
        assertTrue(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldRejectMax() {
        IntegerComponent comp = new IntegerComponent("int123");
        comp.max(10);
        ComponentValue cv = comp.validate("12");
        assertEquals(FormError.MAX_VALUE, cv.getError());
        assertTrue(cv.isErrorPresent());
    }    
    
}
