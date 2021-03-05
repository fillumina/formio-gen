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
    protected ResponseValue innerValidate(List<BigInteger> list) {
        for (BigInteger integer : list) {
            if (min != null && integer.compareTo(min) <= -1) {
                return new ResponseValue(getKey(), list,
                        FormError.MIN_VALUE, integer.toString());
            }
            if (max != null && integer.compareTo(max) >= 1) {
                return new ResponseValue(getKey(), list,
                        FormError.MAX_VALUE, integer.toString());
            }
        }
        return super.innerValidate(list);
    }

    @Override
    public BigInteger convert(Object obj) throws ParseException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigInteger) {
            return (BigInteger) obj;
        }
        if (obj instanceof Number) {
            return BigInteger.valueOf(((Number) obj).longValue());
        }
        try {
            String str = obj.toString();
            return new BigInteger(str);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }
    
}
