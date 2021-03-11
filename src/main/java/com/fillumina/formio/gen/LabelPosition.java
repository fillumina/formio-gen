package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public enum LabelPosition {
    top,
    bottom,
    left_left,
    left_right,
    right_left,
    right_right;

    private String value;

    LabelPosition() {
        this.value = name().replace("_", "-");
    }

    @Override
    public String toString() {
        return value;
    }
}
