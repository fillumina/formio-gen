package com.fillumina.formio.gen;

import java.util.Arrays;
import java.util.Collection;
import org.json.JSONArray;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class TableContainer extends Container<TableContainer> {
    
    private final JSONArray tableRows;
    
    public TableContainer(String key, int rows, int cols) {
        super("table", key);
        json.put("numRows", rows);
        json.put("numCols", cols);
        tableRows = new JSONArray();
        json.put("rows", tableRows);
        for (int row=0; row<rows; row++) {
            JSONArray rowArray = new JSONArray();
            tableRows.put(rowArray);
            for (int col=0; col<cols; col++) {
                JSONArray colArray = new JSONArray();
                rowArray.put(colArray);
            }
        }
    }

    public TableContainer addComponentRowCol(int row, int col, Component component) {
        ((JSONArray)((JSONArray) tableRows.get(row)).get(col))
                .put(component.toJSONObject());
        super.addValidatingComponent(component);
        return this;
    }
    
    public TableContainer striped(boolean striped) {
        json.put("striped", striped);
        return this;
    }
    
    public TableContainer bordered(boolean bordered) {
        json.put("bordered", bordered);
        return this;
    }
    
    public TableContainer hover(boolean hover) {
        json.put("hover", hover);
        return this;
    }
    
    public TableContainer condensed(boolean condensed) {
        json.put("condensed", condensed);
        return this;
    }
    
    public TableContainer headers(String... headers) {
        return headers(Arrays.asList(headers));
    }
    
    public TableContainer headers(Collection<String> headers) {
        JSONArray array = new JSONArray(headers);
        json.put("headers", array);
        return this;
    }
    
}
