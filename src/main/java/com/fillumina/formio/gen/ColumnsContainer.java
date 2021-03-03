package com.fillumina.formio.gen;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Francesco Illuminati <fillumina at gmail.com>
 */
public class ColumnsContainer extends Container<ColumnsContainer> {
    
    private JSONArray columns;
    private ColContainer[] columnArray;
    
    public ColumnsContainer(String key) {
        super("columns", key);
        columns = new JSONArray();
        json.put("columns", columns);
    }
    
    public ColContainer createColumn() {
        JSONObject column = new JSONObject();
        columns.put(column);
        return new ColContainer(column);
    }

    @Override
    public JSONObject toJSONObject() {
        return json;
    }
    
    public class ColContainer {
        private final JSONObject column;
        private final JSONArray components;

        public ColContainer(JSONObject column) {
            this.column = column;
            components = new JSONArray();
            this.column.put("components", components);
        }
        
        /** How many Bootstrap grid units wide is this column  */
        public ColContainer width(int width) {
            column.put("width", width);
            return this;
        }
        
        /** The bootstrap column offset */
        public ColContainer offset(int offset) {
            column.put("offset", offset);
            return this;
        }
        
        /** How many bootstrap grid units to push the column */
        public ColContainer push(int push) {
            column.put("push", push);
            return this;
        }
        
        /** How many bootstrap grid units to pull the column */
        public ColContainer pull(int pull) {
            column.put("pull", pull);
            return this;
        }
        
        public ColContainer addComponent(Component<?,?> component) {
            components.put(component.toJSONObject());
            ColumnsContainer.this.addComponent(component);
            return this;
        }
        
        public ColumnsContainer endCol() {
            return ColumnsContainer.this;
        }
    }
}
