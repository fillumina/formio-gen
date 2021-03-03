package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FieldSetContainer extends ArrayContainer<FieldSetContainer> {
    
    public FieldSetContainer(String key) {
        super("fieldset", key);
    }
    
    public FieldSetContainer legend(String legend) {
        json.put("legend", legend);
        return this;
    }
    
}
