package com.fillumina.formio.gen;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FloatComponentTest {
            
    @Test
    public void shouldRejectValue() {
        FloatComponent comp = new FloatComponent("flt123");
        ComponentValue cv = comp.validate("12,34");
        assertEquals(FormError.NUMBER_FORMAT, cv.getError());
        assertTrue(cv.isErrorPresent());
    }    
            
    @Test
    public void shouldAcceptValue() {
        FloatComponent comp = new FloatComponent("flt123");
        ComponentValue cv = comp.validate("12.34");
        assertFalse(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldAcceptMinMax() {
        FloatComponent comp = new FloatComponent("flt123");
        comp.min(10.99);
        comp.max(20.34);
        ComponentValue cv = comp.validate("12.34");
        assertFalse(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldRejectMin() {
        FloatComponent comp = new FloatComponent("flt123");
        comp.min(10.99);
        ComponentValue cv = comp.validate("2.34");
        assertEquals(FormError.MIN_VALUE, cv.getError());
        assertTrue(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldRejectMax() {
        FloatComponent comp = new FloatComponent("flt123");
        comp.max(10.99);
        ComponentValue cv = comp.validate("12.34");
        assertEquals(FormError.MAX_VALUE, cv.getError());
        assertTrue(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldAcceptMinInclusive() {
        FloatComponent comp = new FloatComponent("flt123");
        comp.min(new BigDecimal("10.99"));
        comp.minInclusive(true);
        ComponentValue cv = comp.validate("10.99");
        assertFalse(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldAcceptMaxInclusive() {
        FloatComponent comp = new FloatComponent("flt123");
        comp.max(new BigDecimal("10.99"));
        comp.maxInclusive(true);
        ComponentValue cv = comp.validate("10.99");
        assertFalse(cv.isErrorPresent());
    }    
    
}
