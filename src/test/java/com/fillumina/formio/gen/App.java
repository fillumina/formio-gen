package com.fillumina.formio.gen;

import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
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
