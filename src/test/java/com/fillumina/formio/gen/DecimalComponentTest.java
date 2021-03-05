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
public class DecimalComponentTest {
            
    @Test
    public void shouldRejectValue() {
        DecimalComponent comp = new DecimalComponent("flt123");
        ResponseValue cv = comp.validate("12,34");
        assertEquals(FormError.PARSE_EXCEPTION, cv.getError());
        assertTrue(cv.isErrorPresent());
    }    
            
    @Test
    public void shouldAcceptValue() {
        DecimalComponent comp = new DecimalComponent("flt123");
        ResponseValue cv = comp.validate("12.34");
        assertFalse(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldAcceptMinMax() {
        DecimalComponent comp = new DecimalComponent("flt123");
        comp.min(10.99);
        comp.max(20.34);
        ResponseValue cv = comp.validate("12.34");
        assertFalse(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldRejectMin() {
        DecimalComponent comp = new DecimalComponent("flt123");
        comp.min(10.99);
        ResponseValue cv = comp.validate("2.34");
        assertEquals(FormError.MIN_VALUE, cv.getError());
        assertTrue(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldRejectMax() {
        DecimalComponent comp = new DecimalComponent("flt123");
        comp.max(10.99);
        ResponseValue cv = comp.validate("12.34");
        assertEquals(FormError.MAX_VALUE, cv.getError());
        assertTrue(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldAcceptMinInclusive() {
        DecimalComponent comp = new DecimalComponent("flt123");
        comp.min(new BigDecimal("10.99"));
        comp.minInclusive(true);
        ResponseValue cv = comp.validate("10.99");
        assertFalse(cv.isErrorPresent());
    }    
    
    @Test
    public void shouldAcceptMaxInclusive() {
        DecimalComponent comp = new DecimalComponent("flt123");
        comp.max(new BigDecimal("10.99"));
        comp.maxInclusive(true);
        ResponseValue cv = comp.validate("10.99");
        assertFalse(cv.isErrorPresent());
    }    
    
}
