package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CurrencyComponentTest {

    @Test
    public void shouldDeclareTheCurrencyType() {
        CurrencyComponent comp = new CurrencyComponent("price123");

        assertEquals("currency", comp.toJSONObject().getString("type"));
    }

    @Test
    public void shouldCarryTheCurrencyItIsShownIn() {
        CurrencyComponent comp = new CurrencyComponent("price123");
        comp.currency("EUR");

        assertEquals("EUR", comp.toJSONObject().getString("currency"));
    }

    @Test
    public void shouldAcceptAnAmount() {
        CurrencyComponent comp = new CurrencyComponent("price123");

        assertFalse(comp.validate("19.99").isErrorPresent());
    }

    @Test
    public void shouldApplyTheRangeRulesOfANumber() {
        CurrencyComponent comp = new CurrencyComponent("price123");
        comp.min(1);
        comp.max(10);

        assertEquals(FormError.MIN_VALUE, comp.validate("0.5").getError());
        assertEquals(FormError.MAX_VALUE, comp.validate("20").getError());
        assertFalse(comp.validate("5").isErrorPresent());
    }

    @Test
    public void shouldDeclareItIsNotAnInteger() {
        CurrencyComponent comp = new CurrencyComponent("price123");

        assertFalse(comp.toJSONObject().getJSONObject("validate").getBoolean("integer"));
    }
}
