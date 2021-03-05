package com.fillumina.formio.gen;

import java.util.Map;
import java.util.Objects;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class FormResponse {
    
    private final Metadata metadata;
    private final Map<String,ResponseValue> map;
    private final boolean errorPresent;

    /**
     * 
     * @param metadata
     * @param map value for label
     * @param errorPresent 
     */
    public FormResponse(Metadata metadata, 
            Map<String,ResponseValue> map, boolean errorPresent) {
        this.metadata = metadata;
        this.map = map;
        this.errorPresent = errorPresent;
    }

    /** @return a clone of the present object with the given metadata. */
    public FormResponse withMetadata(Metadata metadata) {
        return new FormResponse(metadata, this.map, this.errorPresent);
    }
    
    public JSONObject getJsonObject() {
        JSONObject json = new JSONObject();
        map.forEach((k,v) -> json.put(k, v.getJsonObject()) );
        return json;
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
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.map);
        hash = 43 * hash + (this.errorPresent ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FormResponse other = (FormResponse) obj;
        if (this.errorPresent != other.errorPresent) {
            return false;
        }
        if (!Objects.equals(this.map, other.map)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        map.forEach((k,v) -> 
                buf.append(" ").append(k).append(": ").append(v.toString()).append("\n"));
        return "FormResponse: " + (errorPresent ? "" : "no ") + "error present\n" +
                metadata + "\nData:\n" +
                buf.toString() +
                getJsonObject().toString(4);
    }
}
