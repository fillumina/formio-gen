package com.fillumina.formio.gen;

import java.util.Locale;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

/**
 * A repeated component is a form of its own per entry, so an error inside one
 * of them has to reach the response the caller gates on.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class RepeatedComponentValidationTest {

    private static Form formWith(boolean requiredMember) {
        FormBuilder builder = new FormBuilder("f", "F", "f1");
        builder.addComponent(new TextFieldComponent("name123")
                .label("Name").required(true));
        builder.addComponent(new DataGridContainer("members123")
                .addComponent(new TextFieldComponent("who123")
                        .label("Who").required(requiredMember)));
        return builder.build();
    }

    private static JSONObject submissionWith(JSONArray members) {
        return new JSONObject().put("name123", "Ada").put("members123", members);
    }

    @Test
    public void shouldReportAnErrorInsideAnEntry() {
        Form form = formWith(true);

        FormResponse response = form.validateJson(submissionWith(new JSONArray()
                .put(new JSONObject().put("who123", "Grace"))
                .put(new JSONObject())));

        assertTrue(response.isErrorPresent(),
                "a required field missing in a row must not be accepted");
    }

    @Test
    public void shouldNameTheEntryTheErrorIsIn() {
        Form form = formWith(true);

        FormResponse response = form.validateJson(submissionWith(new JSONArray()
                .put(new JSONObject().put("who123", "Grace"))
                .put(new JSONObject())));

        assertTrue(response.getErrorMessage(Locale.ENGLISH)
                        .contains("members123[1]/who123"),
                response.getErrorMessage(Locale.ENGLISH));
    }

    @Test
    public void shouldAcceptEntriesThatAreAllComplete() {
        Form form = formWith(true);

        FormResponse response = form.validateJson(submissionWith(new JSONArray()
                .put(new JSONObject().put("who123", "Grace"))
                .put(new JSONObject().put("who123", "Ada"))));

        assertFalse(response.isErrorPresent(), response.getErrorMessage(Locale.ENGLISH));
    }

    @Test
    public void shouldSurviveAnExplicitlyNullContainer() {
        Form form = formWith(false);

        FormResponse response = form.validateJson(new JSONObject()
                .put("name123", "Ada")
                .put("members123", JSONObject.NULL));

        assertFalse(response.isErrorPresent());
    }

    @Test
    public void shouldSurviveAnEmptyContainer() {
        Form form = formWith(false);

        FormResponse response = form.validateJson(submissionWith(new JSONArray()));

        assertFalse(response.isErrorPresent());
    }

    @Test
    public void shouldReportEachBrokenEntryOnce() {
        Form form = formWith(true);

        FormResponse response = form.validateJson(submissionWith(new JSONArray()
                .put(new JSONObject())
                .put(new JSONObject().put("who123", "Ada"))
                .put(new JSONObject())));

        String[] lines = response.getErrorMessage(Locale.ENGLISH).split("\n");
        assertEquals(2, lines.length, response.getErrorMessage(Locale.ENGLISH));
        assertTrue(lines[0].startsWith("members123[0]/who123"), lines[0]);
        assertTrue(lines[1].startsWith("members123[2]/who123"), lines[1]);
    }
}
