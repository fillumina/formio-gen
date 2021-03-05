package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ButtonComponent<T extends ButtonComponent<T>> extends AbstractNonValueComponent<T> {

    public ButtonComponent(String action) {
        super("button", action);
        json.put("action", action);
    }
    
    public ButtonComponent disableOnInvalid(boolean disableOnInvalid) {
        json.put("disableOnInvalid", disableOnInvalid);
        return this;
    }
    
    public ButtonComponent leftIcon(String leftIcon) {
        json.put("leftIcon", leftIcon);
        return this;
    }
    
    public ButtonComponent rightIcon(String rightIcon) {
        json.put("rightIcon", rightIcon);
        return this;
    }
    
    public ButtonComponent size(String size) {
        json.put("size", size);
        return this;
    }
}
