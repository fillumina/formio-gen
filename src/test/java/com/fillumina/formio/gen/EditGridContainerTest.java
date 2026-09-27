package com.fillumina.formio.gen;

import java.util.LinkedHashMap;
import java.util.Map;
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
public class EditGridContainerTest {

    private Map<String, ResponseValue> validateEntries(EditGridContainer comp, JSONArray submitted) {
        ResponseArray response = (ResponseArray) comp.validate(submitted);
        Map<String, ResponseValue> flat = new LinkedHashMap<>();
        response.addResponseValue(flat);
        return flat;
    }

    @Test
    public void shouldDeclareTheEditGridType() {
        EditGridContainer comp = new EditGridContainer("members123");

        assertEquals("editgrid", comp.toJSONObject().getString("type"));
    }

    @Test
    public void shouldValidateEachEntryAsAFormOfItsOwn() {
        EditGridContainer comp = new EditGridContainer("members123");
        comp.addComponent(new TextFieldComponent("name123").required(true));

        JSONArray submitted = new JSONArray()
                .put(new JSONObject().put("name123", "Ada"))
                .put(new JSONObject().put("name123", "Grace"));

        Map<String, ResponseValue> flat = validateEntries(comp, submitted);

        assertFalse(flat.get("members123[0]/name123").isErrorPresent());
        assertFalse(flat.get("members123[1]/name123").isErrorPresent());
    }

    @Test
    public void shouldReportTheEntryThatIsIncomplete() {
        EditGridContainer comp = new EditGridContainer("members123");
        comp.addComponent(new TextFieldComponent("name123").required(true));

        JSONArray submitted = new JSONArray()
                .put(new JSONObject().put("name123", "Ada"))
                .put(new JSONObject());

        Map<String, ResponseValue> flat = validateEntries(comp, submitted);

        assertFalse(flat.get("members123[0]/name123").isErrorPresent());
        assertTrue(flat.get("members123[1]/name123").isErrorPresent());
        assertEquals(FormError.MISSING, flat.get("members123[1]/name123").getError());
    }
}
