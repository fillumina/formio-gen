package com.fillumina.formio.gen;

import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class FormResponse {
    
    private final JSONObject jsonObject;
    private final Metadata metadata;
    private final Map<String,ResponseValue> map;
    private final boolean errorPresent;

    /**
     * 
     * @param metadata
     * @param map value for label
     * @param errorPresent 
     */
    public FormResponse(JSONObject jsonObject, Metadata metadata, 
            Map<String,ResponseValue> map, boolean errorPresent) {
        this.jsonObject = jsonObject;
        this.metadata = metadata;
        this.map = map;
        this.errorPresent = errorPresent;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public Map<String, ResponseValue> getMap() {
        return map;
    }

    public boolean isErrorPresent() {
        return errorPresent;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        map.forEach((k,v) -> 
                buf.append(" ").append(k).append(": ").append(v.toString()).append("\n"));
        return "FormResponse: " + (errorPresent ? "" : "no ") + "error present\n" +
                metadata + "\nData:\n" +
                buf.toString();
    }
}
