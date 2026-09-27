package com.fillumina.formio.gen;

import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * Checks the assets a generated page asks for. The behaviour of those pages is
 * covered by {@link FormioJsRuntimeTest}, which runs a real browser.
 */
public class CodeGeneratorTest {

    private static final String POST_URL = "/form_post";

    private static String generateHtml(FormioRuntime runtime) {
        JSONObject form = FormCreator.createForm().toFormioJSONObject();
        return CodeGenerator.generateHtml(form, POST_URL, false, runtime);
    }

    @Test
    public void shouldKeepTheUnversionedOverloadOnTheFormio4Line() {
        JSONObject form = FormCreator.createForm().toFormioJSONObject();

        String html = CodeGenerator.generateHtml(form, POST_URL, false);

        assertTrue(html.contains(FormioRuntime.FORMIO_JS_4.jsUrl()), html);
    }

    @ParameterizedTest
    @EnumSource(FormioRuntime.class)
    public void shouldNeverLoadTheUnpinnedFormioCdn(FormioRuntime runtime) {
        String html = generateHtml(runtime);

        assertFalse(html.contains("cdn.form.io/formiojs/formio.full"), html);
    }

    @ParameterizedTest
    @EnumSource(FormioRuntime.class)
    public void shouldNotLoadAssetsFromTheDiscontinuedStackpathCdn(FormioRuntime runtime) {
        String html = generateHtml(runtime);

        assertFalse(html.contains("stackpath"), html);
    }

    @Test
    public void shouldPinTheFormio4LineToItsCdnAndBootstrap4() {
        String html = generateHtml(FormioRuntime.FORMIO_JS_4);

        assertTrue(html.contains("cdn.form.io/formiojs/4.21.2/formio.full.min.js"), html);
        assertTrue(html.contains("bootstrap@4.1.3"), html);
        assertTrue(html.contains("font-awesome@4.7.0"), html);
    }

    @Test
    public void shouldServeTheFormio5LineFromTheNpmPackageWithBootstrap5() {
        String html = generateHtml(FormioRuntime.FORMIO_JS_5);

        assertTrue(html.contains("@formio/js@5.6.1/dist/formio.full.min.js"), html);
        assertTrue(html.contains("bootstrap@5.3.8"), html);
        assertTrue(html.contains("theme: 'bootstrap5'"), html);
        assertFalse(html.contains("font-awesome"), html);
    }

    @Test
    public void shouldNotRequestAThemeOnTheFormio4Line() {
        String html = generateHtml(FormioRuntime.FORMIO_JS_4);

        assertFalse(html.contains("theme:"), html);
    }

    @Test
    public void shouldEmitTheSameFormJsonForEveryRuntime() throws Exception {
        JSONObject form = FormCreator.createForm().toFormioJSONObject();

        String formio4 = CodeGenerator.generateJavascript(form, POST_URL, false,
                FormioRuntime.FORMIO_JS_4);
        String formio5 = CodeGenerator.generateJavascript(form, POST_URL, false,
                FormioRuntime.FORMIO_JS_5);

        String formJson = form.toString(4);
        assertTrue(formio4.contains(formJson), formio4);
        assertTrue(formio5.contains(formJson), formio5);
    }

    @Test
    public void shouldMarkTheFormReadOnlyWhenAsked() {
        JSONObject form = FormCreator.createForm().toFormioJSONObject();

        String html = CodeGenerator.generateHtml(form, POST_URL, true, FormioRuntime.FORMIO_JS_5);

        assertTrue(html.contains("readOnly: true"), html);
    }
}
