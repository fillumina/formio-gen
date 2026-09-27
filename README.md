# Formio-gen

Helper to generate a JSON form description for the [formio](https://github.com/formio/formio) project and validate its returned data.

Requires Java 21 or later.

### See

 * https://help.form.io/
 * https://github.com/formio/formio.js
 * https://formio.github.io/formio.js/app/examples/
 * https://github.com/formio/formio.js/wiki/Form-JSON-Schema
 * https://formio.github.io/formio.js/app/sandbox

### Supported formio.js versions

The form JSON this library produces is the same for every formio.js version. A
`FormioRuntime` only decides which script a generated page loads, and with which
Bootstrap flavour:

| `FormioRuntime` | formio.js | Bootstrap | served by |
| --- | --- | --- | --- |
| `FORMIO_JS_4` | 4.21.2 | 4 | cdn.form.io |
| `FORMIO_JS_5` | 5.6.1 | 5 | jsdelivr, from the `@formio/js` npm package |

Form.io stopped publishing formio.js 5.x to its own CDN, which is why the two
lines come from different hosts. Pass a runtime to
`CodeGenerator.generateHtml(form, postUrl, readOnly, runtime)`; the three
argument overload still targets the 4.x line.

A generated page runs in English and carries the Italian translation as well.
The server side error messages are localised separately, through the
`response_error*.properties` bundles.

### Tests

`mvn test` runs the unit tests and a browser test that renders a generated page
on each supported formio.js line, fills it in, submits it, and feeds the posted
JSON back through `Form.validateJsonFromFormio`. The browser test is skipped
when no browser is found. To run it, install one with `playwright install
chromium`, or point `-Dformio.browser.executable` at a Chromium binary.

### License

Apache License 2.0, see [LICENSE](LICENSE) and [NOTICE](NOTICE). The formio.js
bundles that generated pages load at runtime are MIT licensed by Form.io LLC
and are not part of this distribution.

### Usage example

Using [fluent-http](https://github.com/CodeStory/fluent-http), see [`App.java`](src/test/java/com/fillumina/formio/gen/App.java).

```java
public class App {

    public static void main(String[] args) {
        App app = new App();

        // https://github.com/CodeStory/fluent-http
        new WebServer().configure(routes -> routes
                .get("/", () -> app.createHtml())
                .post("/form_post", context -> {
                    String jsonResponse = context.request().content();
                    app.parseJsonResponse(jsonResponse);
                    return Payload.created();
                })
        ).start();
    }

    private final Form form;
    private JSONObject values;

    public App() {
        this.form = FormCreator.createForm();
    }

    private void parseJsonResponse(String response) {
        System.out.println("RECEIVED JSON: " + response);

        // show parsed response
        FormResponse formResponse = form.validateJsonFromFormio(response);
        System.out.println(formResponse);
        System.out.println("");

        // set values so that the next form regeneration will include them as default
        this.values = formResponse.getJsonObject();
    }

    // dynamically creates HTML code
    private String createHtml() {
        // mix in the received values
        JSONObject jsonForm = form.toFormioJSONObjectAddingValues(values);
        String html = CodeGenerator.generateHtml(jsonForm, "form_post", false);
        return html;
    }
}

```