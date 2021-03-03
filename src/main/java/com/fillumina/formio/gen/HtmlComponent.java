package com.fillumina.formio.gen;

import java.text.ParseException;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class HtmlComponent extends Component<HtmlComponent,Void> {

    public HtmlComponent() {
        super("content", null);
        json.put("input", false);
    }
    
    public HtmlComponent html(String html) {
        json.put("html", html);
        return this;
    }

    @Override
    protected boolean isValue() {
        return false;
    }
    
    @Override
    public Void convert(String s) throws ParseException {
        return null;
    }
}
