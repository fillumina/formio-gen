package com.fillumina.formio.gen;

/**
 * A value that is submitted with the form but never shown to the user.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class HiddenComponent extends StringComponent<HiddenComponent> {

    public HiddenComponent(String key) {
        super("hidden", key);
    }
}
