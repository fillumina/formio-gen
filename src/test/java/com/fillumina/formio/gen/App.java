package com.fillumina.formio.gen;

import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class App {

    public static final App INSTANCE = new App();

    public static void main(String[] args) {
        Form form = INSTANCE.createForm();
        String html = HtmlGenerator.generateHtml(form.toJSONObject());

        new WebServer().configure(routes -> routes
                .get("/", html)
                .post("/form_post", context -> {
                    String jsonResponse = context.request().content();
                    System.out.println("POST: \n" + jsonResponse);
                    INSTANCE.validateJsonResponse(form, jsonResponse);
                    return Payload.created();
                })
        ).start();
    }
    
    private void validateJsonResponse(Form form, String response) {
        FormResponse formResponse = form.validateJson(response);
        System.out.println("END");
    }

    private Form createForm() {
        Form form = new Form("clothing", "Clothing", "123");
        form.addComponent(new BooleanComponent("bool123")
                .label("is this true")
                .placeholder("answer sincerely")
                .required(false));
        form.addComponent(new DayComponent("date123")
                .label("birthday")
                .placeholder("select birthday")
                .required(true));
        form.addComponent(new DateTimeComponent("dt123")
                .label("married")
                .placeholder("select marriage date")
                .datePicker(true)
                .enableTime(false)
                .required(true));
        form.addComponent(new EnumComponent("enum123")
                .label("Sex")
                .placeholder("Say your sex")
                .values("Male", "Female")
                .required(true));
        form.addComponent(new DecimalComponent("float123")
                .label("Height")
                .placeholder("Tell your real height")
                .multipleMin(2)
                .multipleMax(3)
                .required(true));
        form.addComponent(new IntegerComponent("int123")
                .label("Age")
                .placeholder("Tell your real age")
                .required(true));
        form.addComponent(new TextFieldComponent("text123")
                .label("Name")
                .placeholder("Tell your name")
                .required(true));
        form.addComponent(new TextAreaComponent("area123")
                .label("Comment")
                .placeholder("Say something about you")
                .rows(5)
                .required(false));
        form.addComponent(new FieldSetContainer("panel123")
                .label("Panel 1")
                .legend("field set of my dreams")
                //.title("Title panel 1")
                .multiple(true)
                //.theme(Theme._default)
                .addComponent(new TextFieldComponent("tf123")
                        .label("In the panel")
                        .maxLength(20)
                        .minLength(1)));
        form.addComponent(new ColumnsContainer("col123")
            .createColumn().addComponent(new SubmitComponent().label("Send Form")).endCol()
            .createColumn().addComponent(new CancelComponent().label("Clear Data")).endCol());
//        form.addComponent(new SubmitComponent().label("Send Form"));
//        form.addComponent(new CancelComponent().label("Clear Data"));
        return form;
    }
}
