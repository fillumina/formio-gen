package com.fillumina.formio.gen;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class IntegerComponent extends Component<IntegerComponent, BigInteger> {

    private BigInteger min;
    private BigInteger max;

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
    protected ComponentValue innerValidate(List<BigInteger> list) {
        for (BigInteger integer : list) {
            if (min != null && integer.compareTo(min) <= -1) {
                return new ComponentValue(getKey(), list,
                        FormError.MIN_VALUE, integer.toString());
            }
            if (max != null && integer.compareTo(max) >= 1) {
                return new ComponentValue(getKey(), list,
                        FormError.MAX_VALUE, integer.toString());
            }
        }
        return super.innerValidate(list);
    }

    @Override
    public BigInteger convert(String s) throws ParseException {
        try {
            return s == null ? null : new BigInteger(s);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }
    
}
