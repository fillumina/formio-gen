package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CancelComponent extends ButtonComponent<CancelComponent> {

    public CancelComponent() {
        super("reset");
    }

    public CancelComponent showValidations(boolean showValidations) {
        if (showValidations) {
            json.put("showValidations", showValidations);
        } else {
            json.remove("showValidations");
        }
        return this;
    }
}
