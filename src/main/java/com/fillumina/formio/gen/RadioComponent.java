package com.fillumina.formio.gen;

/**
 * A group of radio buttons the user picks a single value from.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class RadioComponent extends OptionComponent<RadioComponent> {

    public RadioComponent(String key) {
        super("radio", key);
    }

    /** Lays the options out on one line instead of stacking them. */
    public RadioComponent inline(Boolean inline) {
        if (inline == Boolean.TRUE) {
            json.put("inline", true);
        } else {
            json.remove("inline");
        }
        return this;
    }
}
