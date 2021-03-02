package com.fillumina.formio.gen;

/**
 *
 * @author fra
 */
public class HtmlComponent extends Component<HtmlComponent> {

    public HtmlComponent() {
        super("content", null);
        json.put("input", false);
    }
    
    public HtmlComponent html(String html) {
        json.put("html", html);
        return this;
    }
}
