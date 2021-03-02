package com.fillumina.formio.gen;

import org.json.JSONObject;

/**
 *
 * @author fra
 */
public class HtmlGenerator {

    private static final String HEADER =
            "<html>\n" +
            "  <head>\n" +
            "  <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
            "  <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\">\n" +
            "  <link rel=\"stylesheet\" href=\"https://cdn.form.io/formiojs/formio.full.min.css\">\n" +
            "  <script src=\"https://cdn.form.io/formiojs/formio.full.min.js\"></script>\n" +
            "    <script type='text/javascript'>\n" +
            "      window.onload = function() {\n" +
            "        Formio.icons = 'fontawesome';\n" +
            "        Formio.createForm(document.getElementById('formio'),\n";

    private static final String JSON_END = ")\n";

    private static final String FORM =
            ".then(function(form) {\n" +
            "  // Defaults are provided as follows.\n" +
            "  form.submission = {\n" +
            "    data: {\n" +
//            "      firstName: 'Joe',\n" +
//            "      lastName: 'Smith'\n" +
            "    }\n" +
            "  };\n" +
            "  function sendJSON(jsonData) {\n" +
            "    // Creating a XHR object \n" +
            "    let xhr = new XMLHttpRequest();\n" +
            "    let url = \"form_post\"; \n" +
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

    public static String generateHtml(JSONObject object) {
        StringBuilder buf = new StringBuilder();

        buf.append(HEADER);
        buf.append(object.toString(4));
        buf.append(JSON_END);
        buf.append(FORM);
        buf.append(FOOTER);

        return buf.toString();
    }

}
