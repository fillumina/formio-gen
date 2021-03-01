package com.fillumina.formio.gen;

/**
 *
 * @author fra
 */
public class TextAreaComponent extends Component<TextAreaComponent> {

    public TextAreaComponent(String key) {
        super("textarea", key);
    }
    
    public TextAreaComponent rows(int rows) {
        json.put("rows", rows);
        return this;
    }
    
    public TextAreaComponent wysiwyg(boolean wysiwyg) {
        json.put("wysiwyg", wysiwyg);
        return this;
    }
}
