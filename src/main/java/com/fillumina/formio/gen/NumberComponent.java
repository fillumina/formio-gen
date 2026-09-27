package com.fillumina.formio.gen;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * A component holding a decimal number, such as a plain number or a currency
 * amount. Not to be used directly.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public abstract class NumberComponent<T extends NumberComponent<T>> extends Component<T, BigDecimal> {

    private BigDecimal min;
    private BigDecimal max;
    private Boolean minInclusive;
    private Boolean maxInclusive;

    protected NumberComponent(String type, String key) {
        super(type, key);
        validate.put("integer", false);
    }

    public T minInclusive(Boolean minInclusive) {
        this.minInclusive = minInclusive;
        return (T) this;
    }

    public T maxInclusive(Boolean maxInclusive) {
        this.maxInclusive = maxInclusive;
        return (T) this;
    }

    public T min(double min) {
        return min(BigDecimal.valueOf(min));
    }

    public T min(BigDecimal min) {
        this.min = min;
        validate.put("min", min.toPlainString());
        return (T) this;
    }

    public T max(double max) {
        return max(BigDecimal.valueOf(max));
    }

    public T max(BigDecimal max) {
        this.max = max;
        validate.put("max", max.toPlainString());
        return (T) this;
    }

    @Override
    protected ResponseValue innerValidate(List<BigDecimal> list) {
        if (list != null) {
            for (BigDecimal dec : list) {
                if (dec != null) {
                    int compareMin = minInclusive == Boolean.TRUE ? 0 : 1;
                    if (min != null && dec.compareTo(min) < compareMin) {
                        return new ResponseValue(getKey(), getPath(), list, isSingleton(),
                                FormError.MIN_VALUE, dec.toPlainString());
                    }
                    int compareMax = maxInclusive == Boolean.TRUE ? 0 : -1;
                    if (max != null && dec.compareTo(max) > compareMax) {
                        return new ResponseValue(getKey(), getPath(), list, isSingleton(),
                                FormError.MAX_VALUE, dec.toPlainString());
                    }
                }
            }
        }
        return super.innerValidate(list);
    }

    @Override
    public BigDecimal convert(Object obj) throws ParseException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        try {
            return new BigDecimal(obj.toString());
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }
}
