package com.fillumina.formio.gen;

/**
 * A telephone number. The default pattern is deliberately permissive and
 * accepts the punctuation used internationally; call
 * {@link #pattern(String)} to require a particular country format.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PhoneNumberComponent extends StringComponent<PhoneNumberComponent> {

    private static final String PHONE_PATTERN = "^\\+?[0-9 ().\\-]{5,}$";

    public PhoneNumberComponent(String key) {
        super("phoneNumber", key);
        pattern(PHONE_PATTERN);
    }
}
