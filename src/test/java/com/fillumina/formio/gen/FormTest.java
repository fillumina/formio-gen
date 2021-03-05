package com.fillumina.formio.gen;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FormTest {

    private static String JSON_HEADER =
            "{\n" +
            "   \"data\":{\n";

    private static String JSON_DATA_EXAMPLE =
            "      \"txt123\":\"hello world\",\n";

    private static String JSON_FOOTER =
            "   },\n" +
            "   \"metadata\":{\n" +
            "      \"timezone\":\"Europe/Rome\",\n" +
            "      \"offset\":60,\n" +
            "      \"origin\":\"http://localhost:8080\",\n" +
            "      \"referrer\":\"\",\n" +
            "      \"browserName\":\"Netscape\",\n" +
            "      \"userAgent\":\"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:86.0) Gecko/20100101 Firefox/86.0\",\n" +
            "      \"pathName\":\"/\",\n" +
            "      \"onLine\":true\n" +
            "   },\n" +
            "   \"state\":\"submitted\"\n" +
            "}";

    @Test
    public void shouldFailIfRequiredComponentIsMissingFromJson() {
        FormBuilder builder = new FormBuilder("form", "Form", "form123");
        final String id = "txt123";
        builder.addComponent(new TextFieldComponent(id).required(true));
        Form form = builder.build();

        // missing data content
        FormResponse response = form.validateJsonFromFormio(JSON_HEADER + JSON_FOOTER);
        assertTrue(response.isErrorPresent());

        ResponseValue componentResponse = response.getMap().get(id);
        assertEquals(FormError.MISSING, componentResponse.getError());
    }

    @Test
    public void shouldAcceptIfRequiredComponentPresent() {
        FormBuilder builder = new FormBuilder("form", "Form", "form123");
        final String id = "txt123";
        builder.addComponent(new TextFieldComponent(id).required(true));
        Form form = builder.build();

        String content = "hello world";
        String jsonContent = "\"txt123\":\"" + content + "\"";
        FormResponse response = form.validateJsonFromFormio(JSON_HEADER + jsonContent + JSON_FOOTER);
        assertFalse(response.isErrorPresent());

        ResponseValue componentResponse = response.getMap().get(id);
        assertEquals(content, componentResponse.getValues().get(0));
    }

    @Test
    public void shouldAcceptIfRequiredComponentListPresent() {
        FormBuilder builder = new FormBuilder("form", "Form", "form123");
        final String id = "txt123";
        builder.addComponent(new TextFieldComponent(id)
                .multiple(true)
                .required(true));
        Form form = builder.build();

        String content = "[\"hello world\",\"this is me\"]";
        String jsonContent = "\"txt123\":" + content;
        FormResponse response = form.validateJsonFromFormio(JSON_HEADER + jsonContent + JSON_FOOTER);
        assertFalse(response.isErrorPresent());

        ResponseValue componentResponse = response.getMap().get(id);
        final List<?> values = componentResponse.getValues();
        assertEquals(2, values.size());
        assertEquals("hello world", values.get(0));
        assertEquals("this is me", values.get(1));
    }
}
