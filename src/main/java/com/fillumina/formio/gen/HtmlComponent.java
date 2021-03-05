package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class HtmlComponent extends AbstractNonValueComponent<HtmlComponent> {

    public HtmlComponent(String key) {
        super("content", key);
        json.put("input", false);
    }
    
    public HtmlComponent html(String html) {
        json.put("html", html);
        return this;
    }
}
