package com.fillumina.formio.gen;

/**
 *
 * @author fra
 */
public class IntegerComponent extends Component<IntegerComponent> {

    public IntegerComponent(String key) {
        super("number", key);
        validate.put("integer", true);
    }
    
    public IntegerComponent min(long min) {
        validate.put("min", min);
        return this;
    }
    
    public IntegerComponent max(long max) {
        validate.put("max", max);
        return this;
    }
}
