# Formio-gen

Helper to generate a JSON form description for the [formio](https://github.com/formio/formio) project and validate its returned data.

### See

 * https://help.form.io/
 * https://github.com/formio/formio.js
 * https://formio.github.io/formio.js/app/examples/
 * https://formio.github.io/formio.js/docs/class/src/components/Components.js~Components.html
 * https://github.com/formio/formio.js/wiki/Form-JSON-Schema
 * https://formio.github.io/formio.js/app/sandbox

### To do

 * Add tests

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