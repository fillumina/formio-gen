package com.fillumina.formio.gen;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
// TODO https://formio.github.io/formio.js/app/examples/htmlview.html  submission view
public class CodeGenerator {

    private static final String HTML_HEADER =
            "<html>\n" +
            "  <head>\n" +
            "  <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n" +
            "  <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
            "  <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\">\n" +
            "  <link rel=\"stylesheet\" href=\"https://cdn.form.io/formiojs/formio.full.min.css\">\n" +
            "  <script src=\"https://cdn.form.io/formiojs/formio.full.min.js\"></script>\n" +
            "    <script type='text/javascript'>\n";

    private static final String JAVASCRIPT_START =
            "      window.onload = function() {\n" +
            "        Formio.icons = 'fontawesome';\n" +
            "        Formio.createForm(document.getElementById('formio'),\n";

    private static final String START_OPTIONS = ", {\n ";

    private static final String CODE_START =
            "  }\n" +
            "}).then(function(form) {\n" +
            "  // Defaults are provided as follows.\n" +
            "  form.submission = {\n" +
            "    data: {\n";

    private static final String CODE_AFTER_DATA =
            //            "      firstName: 'Joe',\n" +
            //            "      lastName: 'Smith'\n" +
            "    }\n" +
            "  };\n" +
            "  function sendJSON(jsonData) {\n" +
            "    // Creating a XHR object \n" +
            "    let xhr = new XMLHttpRequest();\n" +
            "    let url = \"";

    private static final String CODE_END =
            "\"; \n" +
            "\n" +
            "    // open a connection \n" +
            "    xhr.open(\"POST\", url, true); \n" +
            "\n" +
            "    // Set the request header i.e. which type of content you are sending\n" +
            "    xhr.setRequestHeader(\"Content-Type\", \"application/json\");\n" +
            "\n" +
            "    // Create a state change callback\n" +
            "    xhr.onreadystatechange = function () {\n" +
            "        if (xhr.readyState === 4 && xhr.status === 200) {\n" +
            "            // Print received data from server\n" +
            "            console.log(\"received: \", this.responseText);\n" +
            "        }\n" +
            "    };\n" +
            "\n" +
            "    // Sending data with the request\n" +
            "    var jsonText = JSON.stringify(jsonData);\n" +
            "    xhr.send(jsonText);\n" +
            "  }\n" +
            "  \n" +
            "  // Register for the submit event to get the completed submission.\n" +
            "  form.on('submit', function(submission) {\n" +
            "    console.log('sending', submission);\n" +
            "    sendJSON(submission);\n" +
            "    console.log('sent', submission);\n" +
            "    form.emit('submitDone', submission);\n" +
            "  });\n" +
            "  \n" +
            "  // Everytime the form changes, this will fire.\n" +
            "  form.on('change', function(changed) {\n" +
            "    console.log('Form was changed', changed);\n" +
            "  });\n" +
            "});\n" +
            "}\n";

    private static final String FOOTER =
            "    </script>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    <div id='formio'></div>\n" +
            "  </body>\n" +
            "</html>";

    public static String generateHtml(JSONObject object, String postUrl, boolean readOnly) {
        StringBuilder buf = new StringBuilder();
        buf.append(HTML_HEADER);
        buf.append(generateJavascript(object, postUrl, readOnly));
        buf.append(FOOTER);
        return buf.toString();
    }

    public static String generateJavascript(
            JSONObject object, String postUrl, boolean readonly)
            throws JSONException {
        StringBuilder buf = new StringBuilder();
        buf.append(JAVASCRIPT_START);
        buf.append(object.toString(4));
        buf.append(START_OPTIONS);
        if (readonly) {
            buf.append("  readOnly: true,\n");
        }
        buf.append(generateLanguages("it", "en", "it"));
        buf.append(CODE_START);
        buf.append(CODE_AFTER_DATA);
        buf.append(postUrl);
        buf.append(CODE_END);
        return buf.toString();
    }

    // https://github.com/formio/formio.js/wiki/Translations
    private static String generateLanguages(String defLang, String... languages) {
        String header = "language: '" + defLang + "',\n  i18n: {\n";

        List<String> list = new ArrayList<>();
        for (String lang : languages) {
            try (InputStream is = CodeGenerator.class
                    .getResourceAsStream("/translation_" + lang + ".txt")) {
                String txt = new String(is.readAllBytes());
                list.add(txt);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return header + list.stream().collect(Collectors.joining(",\n"));
    }

}
