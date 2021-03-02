package com.fillumina.formio.gen;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class IntegerComponent extends Component<IntegerComponent> {


    private BigInteger min;
    private BigInteger max;
    private Boolean minInclusive;
    private Boolean maxInclusive;
    
    public IntegerComponent(String key) {
        super("number", key);
        validate.put("integer", true);
    }
    
    public IntegerComponent min(BigInteger min) {
        this.min = min;
        validate.put("min", min.toString());
        return this;
    }
    
    public IntegerComponent max(BigInteger max) {
        this.max = max;
        validate.put("max", max.toString());
        return this;
    }
    
    public IntegerComponent min(long min) {
        this.min = BigInteger.valueOf(min);
        validate.put("min", this.min.toString());
        return this;
    }
    
    public IntegerComponent max(long max) {
        this.max = BigInteger.valueOf(max);
        validate.put("max", this.max.toString());
        return this;
    }

    @Override
    protected ComponentValue innerValidate(List<Object> list) {
        for (Object o : list) {
            String s = Objects.toString(o);
            try {
                BigInteger integer = new BigInteger(s);
                int compareMin = minInclusive == Boolean.TRUE ? 0 : -1;
                if (min != null && integer.compareTo(min) <= compareMin) {
                    return new ComponentValue(getKey(), list, 
                            FormError.MIN_VALUE, integer.toString());
                }
                int compareMax = maxInclusive == Boolean.TRUE ? 0 : 1;
                if (max != null && integer.compareTo(max) >= compareMin) {
                    return new ComponentValue(getKey(), list, 
                            FormError.MAX_VALUE, integer.toString());
                }
            } catch (NumberFormatException e) {
                return new ComponentValue(getKey(), list, 
                            FormError.NUMBER_FORMAT, s);
            }
        }
        return super.innerValidate(list);
    }
    
}
