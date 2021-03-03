package com.fillumina.formio.gen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DayComponent extends Component<DayComponent,Date> {
    private static final String FORMAT = "dd/MM/yyyy";
    
    private String timezone = "UTC";
    
    public DayComponent(String key) {
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
    
    public DayComponent timezone(String timezone) {
        this.timezone = timezone;
        return this;
    }
    
    //https://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date/60214805#60214805
    @Override
    public Date convert(String s) throws ParseException {
        if (s == null) {
            return null;
        }
        TimeZone tz = TimeZone.getTimeZone(timezone);
        DateFormat df = new SimpleDateFormat(FORMAT);
        df.setTimeZone(tz);
        Date date = df.parse(s);
        return date;
    }
}
