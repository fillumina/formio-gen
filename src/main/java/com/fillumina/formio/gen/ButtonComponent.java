package com.fillumina.formio.gen;

import java.text.ParseException;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class ButtonComponent extends Component<ButtonComponent,Void> {

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

    @Override
    public Void convert(String s) throws ParseException {
        return null;
    }
}
