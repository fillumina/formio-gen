package com.fillumina.formio.gen;

import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class ParseResponse {
    
    private static String response = "{\"data\":{\"bool123\":false,\"data123\":\"01/01/1900\",\"enum123\":\"Male\",\"float123\":[553],\"text123\":\"dfg\",\"area123\":\"dfg\",\"tf123\":\"\",\"submit\":true,\"reset\":false,\"panel123\":[],\"int123\":345},\"metadata\":{\"timezone\":\"Europe/Rome\",\"offset\":60,\"origin\":\"http://localhost:8080\",\"referrer\":\"\",\"browserName\":\"Netscape\",\"userAgent\":\"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:86.0) Gecko/20100101 Firefox/86.0\",\"pathName\":\"/\",\"onLine\":true},\"state\":\"submitted\"}";

    public static void main(String[] args) {
        JSONObject json = new JSONObject(response);
        
    }
    
}
