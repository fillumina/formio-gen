package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TextAreaComponent extends StringComponent<TextAreaComponent> {

    public TextAreaComponent(String key) {
        super("textarea", key);
    }
    
    public TextAreaComponent rows(int rows) {
        json.put("rows", rows);
        return this;
    }
    
    public TextAreaComponent showWordCount(boolean showWordCount) {
        json.put("showWordCount", showWordCount);
        return this;
    }
    
    public TextAreaComponent showCharCount(boolean showCharCount) {
        json.put("showCharCount", showCharCount);
        return this;
    }
}
