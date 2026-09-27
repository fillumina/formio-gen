package com.fillumina.formio.gen;

/**
 * A dropdown the user picks a single value from.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class EnumComponent extends OptionComponent<EnumComponent> {

    public EnumComponent(String key) {
        super("select", key);
    }

    /**
     * There is a bug (?) in the current formio implementation that if a
     * placeholder is set for type select the specified value would not be
     * considered if set and validation required complains.
     */
    @Override
    public EnumComponent placeholder(String placeholder) {
        return this;
    }
}
