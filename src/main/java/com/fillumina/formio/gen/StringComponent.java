package com.fillumina.formio.gen;

import java.text.ParseException;

/**
 * Not to be used directly.
 * 
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class StringComponent<T extends Component<T,String>> extends Component<T,String> {

    protected StringComponent(String type, String key) {
        super(type, key);
    }
    
    @Override
    public String convert(String s) throws ParseException {
        return s;
    }
}
