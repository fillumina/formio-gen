package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WellComponentTest {

    @Test
    public void shouldDeclareTheWellTypeAndCarryNoValue() {
        WellComponent comp = new WellComponent("note123");

        assertEquals("well", comp.toJSONObject().getString("type"));
        assertFalse(comp.toJSONObject().getBoolean("input"));
        assertFalse(comp.isValue());
    }

    @Test
    public void shouldCarryTheComponentsItWraps() {
        WellComponent comp = new WellComponent("note123");
        comp.addComponent(new TextFieldComponent("inside123"));

        assertEquals(1, comp.toJSONObject().getJSONArray("components").length());
        assertEquals("inside123",
                comp.toJSONObject().getJSONArray("components").getJSONObject(0).getString("key"));
    }

    @Test
    public void shouldCarryATitle() {
        WellComponent comp = new WellComponent("note123");
        comp.title("Before you start");

        assertEquals("Before you start", comp.toJSONObject().getString("title"));
    }
}
