package com.fillumina.formio.gen;

/**
 * Static HTML shown inside the form. Carries no value. Unlike
 * {@link HtmlComponent}, whose markup is the body of a content block, this one
 * is inserted as is, under the tag given by {@link #tag(String)}.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class HtmlElementComponent extends AbstractNonValueComponent<HtmlElementComponent> {

    public HtmlElementComponent(String key) {
        super("htmlelement", key);
        json.put("input", false);
    }

    public HtmlElementComponent content(String content) {
        if (content != null) {
            json.put("content", content);
        } else {
            json.remove("content");
        }
        return this;
    }

    /** @param tag the element the markup is placed in, {@code div} by default */
    public HtmlElementComponent tag(String tag) {
        if (tag != null) {
            json.put("tag", tag);
        } else {
            json.remove("tag");
        }
        return this;
    }
}
