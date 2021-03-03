package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina at gmail.com>
 */
public class DayComponentTest {
 
            
    @Test
    public void shouldRejectValue() {
        DayComponent comp = new DayComponent("day123");
        ComponentValue cv = comp.validate("12,34");
        assertEquals(FormError.PARSE_EXCEPTION, cv.getError());
        assertTrue(cv.isErrorPresent());
    }    
    
}
