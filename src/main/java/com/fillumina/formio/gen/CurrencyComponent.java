package com.fillumina.formio.gen;

/**
 * An amount of money. Behaves like a {@link DecimalComponent} and adds the
 * currency it is shown in.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class CurrencyComponent extends NumberComponent<CurrencyComponent> {

    public CurrencyComponent(String key) {
        super("currency", key);
    }

    /** @param isoCode for example {@code EUR} or {@code USD} */
    public CurrencyComponent currency(String isoCode) {
        json.put("currency", isoCode);
        return this;
    }

    public CurrencyComponent requireSymbol(Boolean requireSymbol) {
        if (requireSymbol == Boolean.TRUE) {
            json.put("requireSymbol", true);
        } else {
            json.remove("requireSymbol");
        }
        return this;
    }

    public CurrencyComponent useGrouping(Boolean useGrouping) {
        if (useGrouping == Boolean.FALSE) {
            json.put("useGrouping", false);
        } else {
            json.remove("useGrouping");
        }
        return this;
    }
}
