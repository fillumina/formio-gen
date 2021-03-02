package com.fillumina.formio.gen;

/**
 *
 * @author fra
 */
public class ButtonComponent extends Component<ButtonComponent> {

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
