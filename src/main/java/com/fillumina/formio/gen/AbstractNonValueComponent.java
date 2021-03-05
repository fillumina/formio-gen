package com.fillumina.formio.gen;

import java.text.ParseException;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class AbstractNonValueComponent<T extends AbstractNonValueComponent<T>> 
        extends Component<T,Void> {

    public AbstractNonValueComponent(String type, String key) {
        super(type, key);
    }

    @Override
    protected boolean isValue() {
        return false;
    }

    @Override
    public Void convert(Object s) throws ParseException {
        return null;
    }
    
}
