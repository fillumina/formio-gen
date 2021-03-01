package com.fillumina.formio.gen;

/**
 *
 * @author fra
 */
public class FloatComponent extends Component<FloatComponent> {

    public FloatComponent(String key) {
        super("number", key);
        validate.put("integer", false);
    }
    
    public FloatComponent min(double min) {
        validate.put("min", min);
        return this;
    }
    
    public FloatComponent max(double max) {
        validate.put("max", max);
        return this;
    }
}
