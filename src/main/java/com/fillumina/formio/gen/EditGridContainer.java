package com.fillumina.formio.gen;

/**
 * A repeatable list of components, where each entry is edited as a form of
 * its own rather than in a table.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class EditGridContainer extends SubFormArrayContainer<EditGridContainer> {

    public EditGridContainer(String key) {
        super("editgrid", key);
        json.put("addAnotherPosition", "bottom");
        json.put("input", true);
    }

    public EditGridContainer addAnotherText(String text) {
        json.put("addAnother", text);
        return this;
    }

    /** Opens each entry in a dialog instead of inline. */
    public EditGridContainer modalEdit(Boolean modalEdit) {
        if (modalEdit == Boolean.TRUE) {
            json.put("modalEdit", true);
        } else {
            json.remove("modalEdit");
        }
        return this;
    }
}
