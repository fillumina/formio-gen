package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PhoneNumberComponentTest {

    @Test
    public void shouldDeclareThePhoneNumberType() {
        PhoneNumberComponent comp = new PhoneNumberComponent("phone123");

        assertEquals("phoneNumber", comp.toJSONObject().getString("type"));
    }

    @Test
    public void shouldAcceptInternationalPunctuation() {
        PhoneNumberComponent comp = new PhoneNumberComponent("phone123");

        assertFalse(comp.validate("+39 06-5555 1234").isErrorPresent());
    }

    @Test
    public void shouldRejectLetters() {
        PhoneNumberComponent comp = new PhoneNumberComponent("phone123");

        assertEquals(FormError.PATTERN_NOT_MATCHING,
                comp.validate("call me maybe").getError());
    }

    @Test
    public void shouldLetTheCallerRequireACountryFormat() {
        PhoneNumberComponent comp = new PhoneNumberComponent("phone123");
        comp.pattern("^\\+39.*$");

        assertFalse(comp.validate("+39 06-5555 1234").isErrorPresent());
        assertTrue(comp.validate("+1 555 1234").isErrorPresent());
    }
}
