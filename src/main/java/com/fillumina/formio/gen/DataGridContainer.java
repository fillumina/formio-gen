package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DataGridContainer extends ArrayContainer<DataGridContainer> {

    public DataGridContainer(String key) {
        super("datagrid", key);
        json.put("addAnotherPosition", "bottom");
        json.put("tableView", true);
        json.put("input", true);
        json.put("tree", true);
    }

    public DataGridContainer addAnotherText(String text) {
        json.put("addAnother", text);
        return this;
    }

    public DataGridContainer striped(Boolean striped) {
        if (striped == Boolean.TRUE) {
            json.put("striped", true);
        }
        return this;
    }

    public DataGridContainer bordered(Boolean bordered) {
        if (bordered == Boolean.TRUE) {
            json.put("bordered", true);
        }
        return this;
    }

    public DataGridContainer hover(Boolean hover) {
        if (hover == Boolean.TRUE) {
            json.put("hover", true);
        }
        return this;
    }

    public DataGridContainer condensed(Boolean condensed) {
        if (condensed == Boolean.TRUE) {
            json.put("condensed", true);
        }
        return this;
    }

    @Override
    public DataGridContainer minItems(Integer minItems) {
        if (minItems != null) {
            validate.put("minLength", minItems);
        }
        return this;
    }

    @Override
    public DataGridContainer maxItems(Integer maxItems) {
        if (maxItems != null) {
            validate.put("maxLength", maxItems);
        }
        return this;
    }

}
