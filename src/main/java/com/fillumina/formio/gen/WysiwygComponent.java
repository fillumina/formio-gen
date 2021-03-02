package com.fillumina.formio.gen;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author fra
 */
public class WysiwygComponent extends TextAreaComponent {

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
    public JSONObject toJSONObject() {
        modules.put("toolbar", toolbar);
        wysiwyg.put("modules", modules);
        json.put("wysiwyg", wysiwyg);
        return super.toJSONObject();
    }
}
