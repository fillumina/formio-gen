package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
import org.json.JSONArray;

public class TagsComponentTest {

    @Test
    public void shouldSubmitValuesAsAnArraySoItemRulesApply() {
        TagsComponent comp = new TagsComponent("tags123");

        assertEquals("tags", comp.toJSONObject().getString("type"));
        assertEquals("array", comp.toJSONObject().getString("storeas"));
    }

    @Test
    public void shouldAcceptAListOfTags() {
        TagsComponent comp = new TagsComponent("tags123");

        ResponseValue cv = comp.validate(new JSONArray().put("red").put("blue"));

        assertFalse(cv.isErrorPresent());
    }

    @Test
    public void shouldRejectTooFewTags() {
        TagsComponent comp = new TagsComponent("tags123");
        comp.minItems(2);

        ResponseValue cv = comp.validate(new JSONArray().put("red"));

        assertEquals(FormError.MULTIPLE_VALUES_TOO_FEW, cv.getError());
    }

    @Test
    public void shouldRejectTooManyTags() {
        TagsComponent comp = new TagsComponent("tags123");
        comp.maxItems(1);

        ResponseValue cv = comp.validate(new JSONArray().put("red").put("blue"));

        assertEquals(FormError.MULTIPLE_VALUES_TOO_MANY, cv.getError());
    }
}
