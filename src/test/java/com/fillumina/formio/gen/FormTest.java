package com.fillumina.formio.gen;

import com.fillumina.formio.gen.Panel.Theme;

/**
 *
 * @author fra
 */
public class FormTest {

    public static void main(String[] args) {
        Form form = new Form("clothing", "Clothing", "123");
        
        form.addComponent(new BooleanComponent("bool123")
                .label("is this true")
                .placeholder("answer sincerely")
                .required(false));
        
        form.addComponent(new DataComponent("data123")
                .label("birthday")
                .placeholder("select birthday")
                .required(true));
        
        form.addComponent(new EnumComponent("enum123")
                .label("Sex")
                .placeholder("Say your sex")
                .values("Male", "Female")
                .required(true));
        
        form.addComponent(new FloatComponent("float123")
                .label("Height")
                .placeholder("Tell your real height")
                .multiple(true)
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
        
        form.addComponent(new Panel("panel123")
                .label("Panel 1")
                .title("Title panel 1")
                .theme(Theme._default)
                .addComponent(new TextFieldComponent("tf123")
                    .label("In the panel")
                    .maxLength(20)
                    .minLength(1)) );
        
        System.out.println(HtmlGenerator.generateHtml(form.toJSONObject()));
        
    }
}
