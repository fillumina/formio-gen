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
public class RadioComponentTest {

    @Test
    public void shouldDeclareTheRadioType() {
        RadioComponent comp = new RadioComponent("sex123");

        assertEquals("radio", comp.toJSONObject().getString("type"));
        assertEquals("values", comp.toJSONObject().getString("dataSrc"));
    }

    @Test
    public void shouldRejectASelectionThatIsNotOffered() {
        RadioComponent comp = new RadioComponent("sex123");
        comp.values("Male", "Female");

        ResponseValue cv = comp.validate("Other");

        assertEquals(FormError.ENUM_ITEM_NOT_PRESENT, cv.getError());
    }

    @Test
    public void shouldAcceptAnOfferedSelection() {
        RadioComponent comp = new RadioComponent("sex123");
        comp.values("Male", "Female");

        assertFalse(comp.validate("Female").isErrorPresent());
    }

    @Test
    public void shouldPublishItsOptionsToTheBrowser() {
        RadioComponent comp = new RadioComponent("sex123");
        comp.values("Male", "Female");

        JSONArray values = comp.toJSONObject().getJSONObject("data").getJSONArray("values");

        assertEquals(2, values.length());
        assertEquals("Female", values.getJSONObject(1).getString("value"));
    }
}
