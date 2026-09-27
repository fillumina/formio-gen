package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class HiddenComponentTest {

    @Test
    public void shouldDeclareTheHiddenType() {
        HiddenComponent comp = new HiddenComponent("usr123");

        assertEquals("hidden", comp.toJSONObject().getString("type"));
    }

    @Test
    public void shouldAcceptTheValueItCarries() {
        HiddenComponent comp = new HiddenComponent("usr123");

        assertFalse(comp.validate("ada").isErrorPresent());
    }

    @Test
    public void shouldRejectAnEmptyValueWhenRequired() {
        HiddenComponent comp = new HiddenComponent("usr123");
        comp.required(true);

        assertEquals(FormError.NULL_VALUE, comp.validate("").getError());
    }
}
