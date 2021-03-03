package com.fillumina.formio.gen;

import java.text.ParseException;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WysiwygComponent extends TextAreaComponent {
    // https://github.com/OWASP/java-html-sanitizer#prepackaged-policies
    private static final PolicyFactory POLICY = Sanitizers.FORMATTING
            .and(Sanitizers.BLOCKS);
    
    private static final PolicyFactory CUSTOM = new HtmlPolicyBuilder()
            .allowElements("a")
            .allowUrlProtocols("https")
            .allowAttributes("href").onElements("a")
            .requireRelNofollowOnLinks()
            .toFactory();
    
    private JSONObject wysiwyg;
    private JSONObject modules;
    private JSONArray toolbar = new JSONArray();
    
    public WysiwygComponent(String key) {
        super(key);
        json.put("wysiwyg", true);
        wysiwyg = new JSONObject();
        toolbar = new JSONArray();
    }
    
    public WysiwygComponent theme(String theme) {
        json.put("theme", theme);
        return this;
    }

    public WysiwygComponent toolbar(String... features) {
        toolbar.put(Arrays.asList(features));
        return this;
    }

    @Override
    public String convert(String txt) throws ParseException {
        // clean text from all dangerous code.
        return POLICY.sanitize(txt);
    }
    
    @Override
    public JSONObject toJSONObject() {
        modules.put("toolbar", toolbar);
        wysiwyg.put("modules", modules);
        json.put("wysiwyg", wysiwyg);
        return super.toJSONObject();
    }
}
