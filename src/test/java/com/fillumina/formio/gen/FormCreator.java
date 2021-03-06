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
                .enableTime(false));
        builder.addComponent(new EnumComponent("enum123")
                .label("Sex")
                .placeholder("Say your sex")
                .values("Male", "Female")
                .requiredIfNotEmptyComponent("dt123"));
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
                .showIfComponentIs("bool123", true));
        builder.addComponent(new TextFieldComponent("text123")
                .label("Name")
                .placeholder("Tell your name")
                .required(true));
        builder.addComponent(new WysiwygComponent("area123", false)
                .label("Comment")
                .placeholder("Say something about you")
                .required(false)
                .showCharCount(true));
        builder.addComponent(new DataGridContainer("datagrid123")
                .label("Panel 1")
                .multiple(true) // multiple doesn't work
                .minItems(1)
                .maxItems(3)
                .addComponent(new BooleanComponent("bool_multiple")
                        .label("inside the fieldset"))
                .addComponent(new TextFieldComponent("text_multiple")
                        .label("In the panel")
                        .disabled(false)
                        .maxLength(20)
                        .minLength(1)));
        builder.addComponent(new ColumnsContainer("col123")
                .createColumn().addComponent(new SubmitComponent().label("Send Form")).endCol()
                .createColumn().addComponent(new CancelComponent().label("Clear Data")).endCol());
        return builder.build();
    }
}
