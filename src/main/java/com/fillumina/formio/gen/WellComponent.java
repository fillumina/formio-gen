package com.fillumina.formio.gen;

/**
 * A highlighted block holding other components, such as a summary panel at
 * the top of a form. Carries no value of its own.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WellComponent extends ArrayContainer<WellComponent> {

    public WellComponent(String key) {
        super("well", key);
        json.put("input", false);
    }

    public WellComponent title(String title) {
        if (title != null) {
            json.put("title", title);
        } else {
            json.remove("title");
        }
        return this;
    }
}
