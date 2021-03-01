package com.fillumina.formio.gen;

import org.json.JSONObject;

/**
 *
 * @author fra
 */
public class DataComponent extends Component<DataComponent> {

    public DataComponent(String key) {
        super("day", key);
        
        JSONObject fields = new JSONObject();
        JSONObject day = new JSONObject();
        JSONObject month = new JSONObject();
        JSONObject year = new JSONObject();

        day.put("type", "text");
        day.put("placeholder", "day");
        day.put("required", "true");
        
        month.put("type", "text");
        month.put("placeholder", "month");
        month.put("required", "true");
        
        year.put("type", "text");
        year.put("placeholder", "year");
        year.put("required", "true");
        
        fields.put("day", day);
        fields.put("month", month);
        fields.put("year", year);
        
        json.put("fields", fields);
        json.put("dayFirst", true);
    }
    
}
