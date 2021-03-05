package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class AbstractTextAreaComponent<T extends StringComponent<T>> extends StringComponent<T> {
    
    protected AbstractTextAreaComponent(String key) {
        super("textarea", key);
    }

    public T rows(int rows) {
        json.put("rows", rows);
        return (T) this;
    }

    public T showWordCount(boolean showWordCount) {
        json.put("showWordCount", showWordCount);
        return (T) this;
    }

    public T showCharCount(boolean showCharCount) {
        json.put("showCharCount", showCharCount);
        return (T) this;
    }
    
}