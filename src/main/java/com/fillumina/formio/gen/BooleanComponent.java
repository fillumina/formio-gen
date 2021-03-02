package com.fillumina.formio.gen;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class BooleanComponent extends Component<BooleanComponent> {

    public BooleanComponent(String key) {
        super("checkbox", key);
    }

    @Override
    protected ComponentValue innerValidate(List<Object> list) {
        for (Object o : list) {
            String s = Objects.toString(o);
            if (!valid(s)) {
                return new ComponentValue(getKey(), list, FormError.BOOLEAN_NOT_FOUND);
            }
        }
        return super.innerValidate(list);
    }
    
    private boolean valid(String str) {
        if (str == null) {
            return false;
        }
        String armonized = str.trim().toUpperCase();
        return "FALSE".equals(armonized) || "TRUE".equals(armonized);
    }
}
