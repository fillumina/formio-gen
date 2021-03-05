package com.fillumina.formio.gen;

import net.codestory.http.WebServer;
import net.codestory.http.payload.Payload;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class App {

    public static final App INSTANCE = new App();

    public static void main(String[] args) {
        Form form = INSTANCE.createForm();
        
        final JSONObject jsonForm = form.toJSONObject();
        
//        Map<String,String> valueMap = Map.of(
//                "age", Value.createSingle("34"),
//                "name", Value.createSingle("Fracchia"),
//                "comment", Value.createSingle("Nel mezzo del cammin di nostra vita"),
//                "sex", Value.createSingle("Male")
//        );
        
        String html = CodeGenerator.generateHtml(jsonForm, "form_post", false);

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
        FormResponse formResponse = form.validateJsonFromFormio(response);
        System.out.println(formResponse);
        System.out.println("");
        FormResponse secondValidation = form.validateJson(formResponse.getJsonObject().toString());
        System.out.println(secondValidation);
        System.out.println("");
        System.out.println((formResponse.equals(secondValidation) ? "" : "NOT ") + "EQUALS");
    }

    private Form createForm() {
        Form form = new Form("clothing", "Clothing", "123");
        form.addComponent(new BooleanComponent("bool123")
                .label("is this true")
                .placeholder("answer sincerely")
                .required(false));
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
                .minItems(1)
                .maxItems(3)
                .required(true)
                .createMultipleInfoComponent());
        form.addComponent(new IntegerComponent("int123")
                .label("Age")
                .placeholder("Tell your real age")
                .required(true));
        form.addComponent(new TextFieldComponent("text123")
                .label("Name")
                .placeholder("Tell your name")
                .required(true));
        form.addComponent(new WysiwygComponent("area123")
                .label("Comment")
                .placeholder("Say something about you")
                // FIXME not working, it must be an upstream bug
                .toolbar(TOOLBAR)
                .rows(5)
                .required(false)
                .showCharCount(true));
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

    private static final String TOOLBAR =
            "[['bold', 'italic', 'underline', 'strike']]\n";

    private static final String TOOLBAR_1 =
            "        [\n" +
            "          ['bold', 'italic', 'underline', 'strike'],\n" +
            "          ['blockquote', 'code-block'],\n" +
            "\n" +
            "          [{ 'list': 'ordered'}, { 'list': 'bullet' }],\n" +
            "          [{ 'indent': '-1'}, { 'indent': '+1' }],\n" +
            "          [{ 'direction': 'rtl' }],\n" +
            "\n" +
            "          [{ 'size': ['small', false, 'large', 'huge'] }],\n" +
            "\n" +
            "          [{ 'color': [] }, { 'background': [] }],\n" +
            "          [{ 'font': [] }],\n" +
            "          [{ 'align': [] }],\n" +
            "\n" +
            "          ['clean']\n" +
            "        ]";

    private static final String TOOLBAR_OK =
            "        [\n" +
            "          ['bold', 'italic', 'underline', 'strike'],\n" +
            "          ['blockquote', 'code-block'],\n" +
            "\n" +
            "          [{ 'header': 1 }, { 'header': 2 }],\n" +
            "          [{ 'list': 'ordered'}, { 'list': 'bullet' }],\n" +
            "          [{ 'script': 'sub'}, { 'script': 'super' }],\n" +
            "          [{ 'indent': '-1'}, { 'indent': '+1' }],\n" +
            "          [{ 'direction': 'rtl' }],\n" +
            "\n" +
            "          [{ 'size': ['small', false, 'large', 'huge'] }],\n" +
            "          [{ 'header': [1, 2, 3, 4, 5, 6, false] }],\n" +
            "\n" +
            "          [{ 'color': [] }, { 'background': [] }],\n" +
            "          [{ 'font': [] }],\n" +
            "          [{ 'align': [] }],\n" +
            "\n" +
            "          ['clean']\n" +
            "        ]";
}
