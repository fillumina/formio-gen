package com.fillumina.formio.gen;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class FloatComponent extends Component<FloatComponent> {

    private BigDecimal min;
    private BigDecimal max;
    private Boolean minInclusive;
    private Boolean maxInclusive;
    
    public FloatComponent(String key) {
        super("number", key);
        validate.put("integer", false);
    }
    
    public FloatComponent minInclusive(Boolean minInclusive) {
        this.minInclusive = minInclusive;
        return this;
    }
    
    public FloatComponent maxInclusive(Boolean maxInclusive) {
        this.maxInclusive = maxInclusive;
        return this;
    }
    
    public FloatComponent min(double min) {
        this.min = BigDecimal.valueOf(min);
        validate.put("min", this.min.toPlainString());
        return this;
    }
    
    public FloatComponent max(double max) {
        this.max = BigDecimal.valueOf(max);
        validate.put("max", this.max.toPlainString());
        return this;
    }
    
    public FloatComponent min(BigDecimal min) {
        this.min = min;
        validate.put("min", min.toPlainString());
        return this;
    }
    
    public FloatComponent max(BigDecimal max) {
        this.max = max;
        validate.put("max", max.toPlainString());
        return this;
    }

    @Override
    protected ComponentValue innerValidate(List<Object> list) {
        for (Object o : list) {
            String s = Objects.toString(o);
            try {
                BigDecimal dec = new BigDecimal(s);
                int compareMin = minInclusive == Boolean.TRUE ? 0 : -1;
                if (min != null && dec.compareTo(min) <= compareMin) {
                    return new ComponentValue(getKey(), list, 
                            FormError.MIN_VALUE, dec.toPlainString());
                }
                int compareMax = maxInclusive == Boolean.TRUE ? 0 : 1;
                if (max != null && dec.compareTo(max) >= compareMin) {
                    return new ComponentValue(getKey(), list, 
                            FormError.MAX_VALUE, dec.toPlainString());
                }
            } catch (NumberFormatException e) {
                return new ComponentValue(getKey(), list, 
                            FormError.NUMBER_FORMAT, s);
            }
        }
        return super.innerValidate(list);
    }
}
