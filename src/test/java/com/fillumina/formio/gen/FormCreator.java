package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FormCreator {

    public static Form createForm() {
        FormBuilder builder = new FormBuilder("clothing", "Clothing", "123");
        builder.addComponent(new BooleanComponent("bool123")
                .label("is this true")
                .placeholder("answer sincerely")
                .required(false));
        builder.addComponent(new DateTimeComponent("dt123")
                .label("married")
                .placeholder("select marriage date")
                .datePicker(true)
                .enableTime(false)
                .required(true));
        builder.addComponent(new EnumComponent("enum123")
                .label("Sex")
                .placeholder("Say your sex")
                .values("Male", "Female")
                .required(true));
        builder.addComponent(new DecimalComponent("float123")
                .label("Height")
                .placeholder("Tell your real height")
                .minItems(1)
                .maxItems(3)
                .required(true)
                .createMultipleInfoComponent());
        builder.addComponent(new IntegerComponent("int123")
                .label("Age")
                .placeholder("Tell your real age")
                .required(true));
        builder.addComponent(new TextFieldComponent("text123")
                .label("Name")
                .placeholder("Tell your name")
                .required(true));
        builder.addComponent(new WysiwygComponent("area123")
                .label("Comment")
                .placeholder("Say something about you")
                // FIXME not working, it must be an upstream bug
                .toolbar(TOOLBAR)
                .rows(5)
                .required(false)
                .showCharCount(true));
        builder.addComponent(new FieldSetContainer("panel123")
                .label("Panel 1")
                .legend("field set of my dreams")
                //.title("Title panel 1")
                .multiple(true)
                //.theme(Theme._default)
                .addComponent(new TextFieldComponent("tf123")
                        .label("In the panel")
                        .maxLength(20)
                        .minLength(1)));
        builder.addComponent(new ColumnsContainer("col123")
                .createColumn().addComponent(new SubmitComponent().label("Send Form")).endCol()
                .createColumn().addComponent(new CancelComponent().label("Clear Data")).endCol());
//        form.addComponent(new SubmitComponent().label("Send FormBuilder"));
//        form.addComponent(new CancelComponent().label("Clear Data"));
        return builder.build();
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
