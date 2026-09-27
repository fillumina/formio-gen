package com.fillumina.formio.gen;

import java.util.Locale;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

/**
 * A repeated section is the one kind of container the user fills in, so a
 * required one has to be present and is allowed to be empty only when it is
 * not required.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class RequiredRepeatedSectionTest {

    private static DataGridContainer datagrid(Integer minItems) {
        DataGridContainer grid = new DataGridContainer("members123");
        grid.addComponent(new TextFieldComponent("who123").label("Who"));
        if (minItems != null) {
            grid.minItems(minItems);
        }
        return grid;
    }

    private static Form formWith(DataGridContainer grid) {
        FormBuilder builder = new FormBuilder("f", "F", "f1");
        builder.addComponent(grid);
        return builder.build();
    }

    @Test
    public void shouldWriteTheRowCountIntoValidateRatherThanATextLength() {
        JSONObject validate = datagrid(1).toJSONObject().getJSONObject("validate");

        assertEquals(1, validate.getInt("minItems"));
        assertFalse(validate.has("minLength"), validate.toString());
    }

    @Test
    public void shouldRejectASectionWithNoRowsWhenItAsksForOne() {
        Form form = formWith(datagrid(1));

        FormResponse response = form.validateJson(
                new JSONObject().put("members123", new JSONArray()));

        assertTrue(response.isErrorPresent(), response.getErrorMessage(Locale.ENGLISH));
    }

    @Test
    public void shouldRejectASectionThatIsMissingWhenItAsksForOne() {
        Form form = formWith(datagrid(1));

        FormResponse response = form.validateJson(new JSONObject());

        assertTrue(response.isErrorPresent(), response.getErrorMessage(Locale.ENGLISH));
        assertTrue(response.getErrorMessage(Locale.ENGLISH).contains("members123"),
                response.getErrorMessage(Locale.ENGLISH));
    }

    @Test
    public void shouldAcceptASectionWithRows() {
        Form form = formWith(datagrid(1));

        FormResponse response = form.validateJson(new JSONObject()
                .put("members123", new JSONArray()
                        .put(new JSONObject().put("who123", "Ada"))));

        assertFalse(response.isErrorPresent(), response.getErrorMessage(Locale.ENGLISH));
    }

    @Test
    public void shouldAcceptAnEmptySectionWhenNoRowCountWasAskedFor() {
        Form form = formWith(datagrid(null));

        FormResponse response = form.validateJson(
                new JSONObject().put("members123", new JSONArray()));

        assertFalse(response.isErrorPresent(), response.getErrorMessage(Locale.ENGLISH));
    }

    @Test
    public void shouldStillPushRequiredDownIntoTheRows() {
        DataGridContainer grid = new DataGridContainer("members123");
        TextFieldComponent who = new TextFieldComponent("who123");
        grid.addComponent(who);

        grid.required(true);

        assertTrue(grid.isRequired());
        assertTrue(who.isRequired());
    }

    @Test
    public void shouldNotExpectALayoutContainerInTheSubmission() {
        PanelContainer panel = new PanelContainer("panel123");
        panel.addComponent(new TextFieldComponent("inside123"));
        panel.required(true);

        FormBuilder builder = new FormBuilder("f", "F", "f1");
        builder.addComponent(panel);

        assertFalse(panel.isExpectedInSubmission());
        assertTrue(panel.isRequired());

        FormResponse response = builder.build()
                .validateJson(new JSONObject().put("inside123", "Ada"));
        assertFalse(response.isErrorPresent(), response.getErrorMessage(Locale.ENGLISH));
    }
}
