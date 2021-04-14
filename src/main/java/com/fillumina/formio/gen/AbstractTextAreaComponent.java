package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractTextAreaComponent<T extends StringComponent<T>>
        extends StringComponent<T> {

    protected AbstractTextAreaComponent(String key) {
        super("textarea", key);
        rows(1);
    }

    public T allowSpellCheck(boolean spellcheck) {
        json.put("spellcheck", spellcheck);
        return (T) this;
    }

    public T inputFormat(TextInputFormat textInputFormat) {
        json.put("inputFormat", textInputFormat.toString());
        return (T) this;
    }

    public T autoExpand(boolean autoExpand) {
        json.put("autoExpand", autoExpand);
        return (T) this;
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
