package com.fillumina.formio.gen;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
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
        assertEquals(content, componentResponse.getValueList().get(0));
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
        final List<?> values = componentResponse.getValueList();
        assertEquals(2, values.size());
        assertEquals("hello world", values.get(0));
        assertEquals("this is me", values.get(1));
    }

    @Test
    public void shouldValidateTheReturnedJson() {
        Form form = FormCreator.createForm();
        JSONObject json = new JSONObject();
        json.put("bool123", false);
        json.put("dt123", "2021-03-11T00:00:00+01:00");
        json.put("enum123", "Male");
        json.put("float123", new JSONArray(List.of(12.3, 24.6)));
        json.put("text123", "Elementare whatson");
        json.put("area123", "Nel mezzo del cammin di nostra vita");
        json.put("int123", 12);

        String jsonText = FormioMessageCreator.createFormioJson(json);

        FormResponse response = form.validateJsonFromFormio(jsonText);
        assertFalse(response.isErrorPresent());

        assertEquals(false,
                extractValue(response, "bool123").get(0));
        assertEquals("2021-03-11T00:00:00+01:00",
                extractValue(response, "dt123").get(0).toString());
        assertEquals("Male",
                extractValue(response, "enum123").get(0));
        assertEquals(List.of(BigDecimal.valueOf(12.3), BigDecimal.valueOf(24.6)),
                extractValue(response, "float123"));
        assertEquals("Elementare whatson",
                extractValue(response, "text123").get(0));
        assertEquals("Nel mezzo del cammin di nostra vita",
                extractValue(response, "area123").get(0));
        assertEquals(BigInteger.valueOf(12),
                extractValue(response, "int123").get(0));
    }

    private static List<?> extractValue(FormResponse response, String key) {
        return response.getMap().get(key).getValueList();
    }
}
