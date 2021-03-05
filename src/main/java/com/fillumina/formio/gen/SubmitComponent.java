package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class SubmitComponent extends ButtonComponent<CancelComponent> {

    public SubmitComponent() {
        super("submit");
    }

    public SubmitComponent disableOnInvalid(boolean disableOnInvalid) {
        json.put("disableOnInvalid", disableOnInvalid);
        return this;
    }
}
