package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FieldSet extends Container<FieldSet> {
    
    public FieldSet(String key) {
        super("fieldset", key);
    }
    
    public FieldSet legend(String legend) {
        json.put("legend", legend);
        return this;
    }
    
}
