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
public class SelectBoxesComponentTest {

    private JSONObject ticked(String value) {
        return new JSONObject().put(value, true);
    }

    @Test
    public void shouldDeclareTheSelectBoxesType() {
        SelectBoxesComponent comp = new SelectBoxesComponent("colours123");

        assertEquals("selectboxes", comp.toJSONObject().getString("type"));
        assertEquals("values", comp.toJSONObject().getString("dataSrc"));
    }

    @Test
    public void shouldAcceptSeveralTickedBoxes() {
        SelectBoxesComponent comp = new SelectBoxesComponent("colours123");
        comp.values("red", "green", "blue");

        JSONObject submitted = ticked("red").put("blue", true).put("green", false);

        assertFalse(comp.validate(submitted).isErrorPresent());
    }

    @Test
    public void shouldRejectABoxThatWasNeverOffered() {
        SelectBoxesComponent comp = new SelectBoxesComponent("colours123");
        comp.values("red", "green");

        ResponseValue cv = comp.validate(ticked("purple"));

        assertEquals(FormError.ENUM_ITEM_NOT_PRESENT, cv.getError());
    }

    @Test
    public void shouldRejectABoxThatIsNotTrueOrFalse() {
        SelectBoxesComponent comp = new SelectBoxesComponent("colours123");
        comp.values("red", "green");

        ResponseValue cv = comp.validate(new JSONObject().put("red", "yes"));

        assertEquals(FormError.PARSE_EXCEPTION, cv.getError());
    }

    @Test
    public void shouldAcceptTheEmptyKeyFormioSendsWhenNothingIsTicked() {
        SelectBoxesComponent comp = new SelectBoxesComponent("colours123");
        comp.values("red", "green");

        ResponseValue cv = comp.validate(new JSONObject().put("", false));

        assertFalse(cv.isErrorPresent());
    }

    @Test
    public void shouldRejectNothingTickedWhenRequired() {
        SelectBoxesComponent comp = new SelectBoxesComponent("colours123");
        comp.values("red", "green");
        comp.required(true);

        assertEquals(FormError.NULL_VALUE,
                comp.validate(new JSONObject().put("red", false)).getError());
        assertFalse(comp.validate(ticked("red")).isErrorPresent());
    }
}
