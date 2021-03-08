package com.fillumina.formio.gen;

import java.text.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WysiwygComponent extends AbstractTextAreaComponent<WysiwygComponent> {
    
    // https://github.com/OWASP/java-html-sanitizer#prepackaged-policies
    private static final PolicyFactory POLICY = Sanitizers.FORMATTING
            .and(Sanitizers.BLOCKS);
    
    private static final PolicyFactory CUSTOM = new HtmlPolicyBuilder()
            .allowElements("a")
            .allowUrlProtocols("https")
            .allowAttributes("href").onElements("a")
            .requireRelNofollowOnLinks()
            .toFactory();
    
    private JSONObject wysiwyg = new JSONObject();
    private JSONObject modules = new JSONObject();
    
    public WysiwygComponent(String key) {
        super(key);
        json.put("wysiwyg", true);
        wysiwyg = new JSONObject();
    }
    
    public WysiwygComponent theme(String theme) {
        json.put("theme", theme);
        return this;
    }

    /**
     * Define the toolbar features using a JSON formatted string with the following kind of data:
     * 
     * <pre>
        [
          ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
          ['blockquote', 'code-block'],

          [{ 'header': 1 }, { 'header': 2 }],               // customValidation button values
          [{ 'list': 'ordered'}, { 'list': 'bullet' }],
          [{ 'script': 'sub'}, { 'script': 'super' }],      // superscript/subscript
          [{ 'indent': '-1'}, { 'indent': '+1' }],          // outdent/indent
          [{ 'direction': 'rtl' }],                         // text direction

          [{ 'size': ['small', false, 'large', 'huge'] }],  // customValidation dropdown
          [{ 'header': [1, 2, 3, 4, 5, 6, false] }],

          [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
          [{ 'font': [] }],
          [{ 'align': [] }],

          ['clean']                                         // remove formatting button
        ]
 </pre>
     * 
     * @see https://quilljs.com/docs/modules/toolbar/
     * @param features
     * @return 
     */
    public WysiwygComponent toolbar(String json) {
        modules.put("toolbar", new JSONArray(json));
        return this;
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
        wysiwyg.put("modules", modules);
        json.put("wysiwyg", wysiwyg);
        return super.toJSONObject();
    }
}
