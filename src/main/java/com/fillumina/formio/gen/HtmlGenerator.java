package com.fillumina.formio.gen;

import org.json.JSONObject;

/**
 *
 * @author fra
 */
public class HtmlGenerator {

    private static final String HEADER = "<html>\n" +
            "  <head>\n" +
            "    <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>\n" +
            "    <link rel='stylesheet' href='https://unpkg.com/formiojs@latest/dist/formio.full.min.css'>\n" +
            "    <script src='https://unpkg.com/formiojs@latest/dist/formio.full.min.js'></script>\n" +
            "    <script type='text/javascript'>\n" +
            "      window.onload = function() {\n" +
            "        Formio.createForm(document.getElementById('formio'),\n";

    private static final String FOOTER = ");\n" +
            "      };\n" +
            "    </script>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <div id='formio'></div>\n" +
            "  </body>\n" +
            "</html>";

    public static String generateHtml(JSONObject object) {
        StringBuilder buf = new StringBuilder();
        
        buf.append(HEADER);
        buf.append(object.toString(4));
        buf.append(FOOTER);
        
        return buf.toString();
    }

}
