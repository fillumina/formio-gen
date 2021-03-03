package com.fillumina.formio.gen;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class DecimalComponent extends Component<DecimalComponent,BigDecimal> {

    private BigDecimal min;
    private BigDecimal max;
    private Boolean minInclusive;
    private Boolean maxInclusive;
    
    public DecimalComponent(String key) {
        super("number", key);
        validate.put("integer", false);
    }
    
    public DecimalComponent minInclusive(Boolean minInclusive) {
        this.minInclusive = minInclusive;
        return this;
    }
    
    public DecimalComponent maxInclusive(Boolean maxInclusive) {
        this.maxInclusive = maxInclusive;
        return this;
    }
    
    public DecimalComponent min(double min) {
        this.min = BigDecimal.valueOf(min);
        validate.put("min", this.min.toPlainString());
        return this;
    }
    
    public DecimalComponent max(double max) {
        this.max = BigDecimal.valueOf(max);
        validate.put("max", this.max.toPlainString());
        return this;
    }
    
    public DecimalComponent min(BigDecimal min) {
        this.min = min;
        validate.put("min", min.toPlainString());
        return this;
    }
    
    public DecimalComponent max(BigDecimal max) {
        this.max = max;
        validate.put("max", max.toPlainString());
        return this;
    }

    @Override
    protected ComponentValue innerValidate(List<BigDecimal> list) {
        if (list != null) {
            for (BigDecimal dec : list) {
                if (dec != null) {
                    int compareMin = minInclusive == Boolean.TRUE ? 0 : 1;
                    if (min != null && dec.compareTo(min) < compareMin) {
                        return new ComponentValue(getKey(), list, 
                                FormError.MIN_VALUE, dec.toPlainString());
                    }
                    int compareMax = maxInclusive == Boolean.TRUE ? 0 : -1;
                    if (max != null && dec.compareTo(max) > compareMax) {
                        return new ComponentValue(getKey(), list, 
                                FormError.MAX_VALUE, dec.toPlainString());
                    }
                }
            }
        }
        return super.innerValidate(list);
    }

    @Override
    public BigDecimal convert(String s) throws ParseException {
        try {
            return s == null ? null : new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }
}
