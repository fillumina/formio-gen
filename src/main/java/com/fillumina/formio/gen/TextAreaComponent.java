package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TextAreaComponent extends Component<TextAreaComponent> {

    public TextAreaComponent(String key) {
        super("textarea", key);
    }
    
    public TextAreaComponent rows(int rows) {
        json.put("rows", rows);
        return this;
    }
}
