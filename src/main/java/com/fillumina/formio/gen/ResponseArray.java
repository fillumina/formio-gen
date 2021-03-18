package com.fillumina.formio.gen;

import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ResponseArray extends ResponseValue {

    private final List<FormResponse> formResponseList;

    public ResponseArray(String key, String path, List<?> values, boolean singleton,
            List<FormResponse> formResponseList) {
        super(key, path, values, singleton);
        this.formResponseList = formResponseList;
    }

    public ResponseArray(String key, String path, List<?> values, boolean singleton,
            FormError error, List<FormResponse> formResponseList,
            Object... validationParameters) {
        super(key, path, values, singleton, error, validationParameters);
        this.formResponseList = formResponseList;
    }

    /* package */ void addResponseValue(Map<String, ResponseValue> flatMap) {
        int index = 0;
        for (FormResponse fr : formResponseList) {
            String prefix = getPath() + "[" + index + "]/";
            fr.recursiveAddResponseValue(prefix, flatMap);
            index++;
        }
    }

    @Override
    public Object getJsonObject() {
        JSONArray array = new JSONArray();
        for (FormResponse fr : formResponseList) {
            JSONObject obj = fr.getJsonObject();
            array.put(obj);
        }
        return array;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(System.lineSeparator());
        int index = 0;
        for (FormResponse fr : formResponseList) {
            String prefix = "    " + getKey() + "[" + index + "]/";
            fr.getMap().forEach((n,v) -> {
                buf.append(prefix).append(n).append(": ")
                        .append(v).append(System.lineSeparator());
            });
            index++;
        }
        return buf.toString();
    }
}
