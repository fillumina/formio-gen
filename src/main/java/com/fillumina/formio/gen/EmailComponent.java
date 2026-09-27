package com.fillumina.formio.gen;

/**
 * An email address. Carries a permissive format check that the browser and
 * the Java validator both apply; call {@link #pattern(String)} to tighten it.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class EmailComponent extends StringComponent<EmailComponent> {

    private static final String EMAIL_PATTERN = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

    public EmailComponent(String key) {
        super("email", key, false);
        pattern(EMAIL_PATTERN);
    }
}
