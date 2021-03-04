package com.fillumina.formio.gen;

import java.util.Map;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class FormResponse {
    
    private final Metadata metadata;
    private final Map<String,ComponentValue> map;
    private final boolean errorPresent;

    /**
     * 
     * @param metadata
     * @param map value for label
     * @param errorPresent 
     */
    public FormResponse(Metadata metadata, Map<String,ComponentValue> map, boolean errorPresent) {
        this.metadata = metadata;
        this.map = map;
        this.errorPresent = errorPresent;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public Map<String, ComponentValue> getMap() {
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
