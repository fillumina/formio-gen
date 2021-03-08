package com.fillumina.formio.gen;

import java.text.ParseException;
import org.json.JSONObject;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class LineNumberedTextAreaComponent extends AbstractTextAreaComponent<LineNumberedTextAreaComponent> {

    // https://github.com/OWASP/java-html-sanitizer#prepackaged-policies
    private static final PolicyFactory POLICY = Sanitizers.FORMATTING
            .and(Sanitizers.BLOCKS);

    private static final PolicyFactory CUSTOM = new HtmlPolicyBuilder()
            .allowElements("a")
            .allowUrlProtocols("https")
            .allowAttributes("href").onElements("a")
            .requireRelNofollowOnLinks()
            .toFactory();


    public LineNumberedTextAreaComponent(String key) {
        super(key);
        json.put("editor", "ace");
    }

    @Override
    public String convert(Object obj) throws ParseException {
        if (obj == null) {
            return null;
        }
        // clean text from all dangerous code.
        return POLICY.sanitize(obj.toString());
    }

    @Override
    public JSONObject toJSONObject() {
        return super.toJSONObject();
    }
}
