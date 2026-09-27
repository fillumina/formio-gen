package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PasswordComponentTest {

    @Test
    public void shouldDeclareThePasswordTypeAndProtectTheValue() {
        PasswordComponent comp = new PasswordComponent("pwd123");

        assertEquals("password", comp.toJSONObject().getString("type"));
        assertTrue(comp.toJSONObject().getBoolean("protected"));
    }

    @Test
    public void shouldAcceptAnyPasswordWhenNoRuleIsSet() {
        PasswordComponent comp = new PasswordComponent("pwd123");

        assertFalse(comp.validate("hunter2").isErrorPresent());
    }

    @Test
    public void shouldApplyLengthRules() {
        PasswordComponent comp = new PasswordComponent("pwd123");
        comp.minLength(8);

        assertEquals(FormError.LENGTH_TOO_SHORT, comp.validate("short").getError());
        assertFalse(comp.validate("longenough").isErrorPresent());
    }
}
