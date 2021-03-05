package com.fillumina.formio.gen;

import java.text.ParseException;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class BooleanComponent extends Component<BooleanComponent,Boolean> {

    public BooleanComponent(String key) {
        super("checkbox", key);
    }

    @Override
    public Boolean convert(Object obj) throws ParseException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        final String str = obj.toString();
        String armonized = str.trim().toUpperCase();
        if (!"FALSE".equals(armonized) && !"TRUE".equals(armonized)) {
            throw new ParseException(str, 0);
        }
        return "TRUE".equals(armonized);
    }
    
}
