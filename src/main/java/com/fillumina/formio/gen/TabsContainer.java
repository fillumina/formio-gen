package com.fillumina.formio.gen;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Documentation for tabs is lacking, a code example can be
 * extracted from <a href='https://formio.github.io/formio.js/app/sandbox'>sandbox</a>.
 * <pre>
{
  "display": "form",
  "components": [
    {
      "input": false,
      "key": "tabs",
      "tableView": false,
      "label": "Tabs",
      "type": "tabs",
      "components": [
        {
          "label": "Tab 1",
          "key": "tab1",
          "components": []
        }
      ]
    },
    {
      "type": "button",
      "label": "Submit",
      "key": "submit",
      "disableOnInvalid": true,
      "input": true,
      "tableView": false
    }
  ]
}
 * </pre>
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TabsContainer extends Container<TabsContainer> {

    private final JSONArray components;

    public TabsContainer(String key) {
        super("tabs", key);
        components = new JSONArray();
        json.put("components", components);
    }

    public Tab createTab(String key, String label) {
        JSONObject tabObject = new JSONObject();
        components.put(tabObject);
        tabObject.put("key", key);
        tabObject.put("label", label);
        JSONArray tabComponents = new JSONArray();
        tabObject.put("components", tabComponents);
        return new Tab(tabComponents);
    }

    public class Tab {
        private final JSONArray array;

        public Tab(JSONArray array) {
            this.array = array;
        }

        public Tab addComponent(Component<?,?>... components) {
            for (Component<?,?> component : components) {
                array.put(component.toJSONObject());
                TabsContainer.this.addValidatingComponent(component);
            }
            return this;
        }

        public TabsContainer endTab() {
            return TabsContainer.this;
        }
    }

}
