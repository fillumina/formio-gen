package com.fillumina.formio.gen;

import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FormioMessageCreator {

    private static String METADATA =
            "   {\n" +
            "      \"timezone\":\"Europe/Rome\",\n" +
            "      \"offset\":60,\n" +
            "      \"origin\":\"http://localhost:8080\",\n" +
            "      \"referrer\":\"\",\n" +
            "      \"browserName\":\"Netscape\",\n" +
            "      \"userAgent\":\"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:86.0) Gecko/20100101 Firefox/86.0\",\n" +
            "      \"pathName\":\"/\",\n" +
            "      \"onLine\":true\n" +
            "   }";


    public static String createFormioJson(JSONObject object) {
        JSONObject json = new JSONObject();
        json.put("data", object);
        json.put("metadata", new JSONObject(METADATA));
        return json.toString(4);
    }
}
