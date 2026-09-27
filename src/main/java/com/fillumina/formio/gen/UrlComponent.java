package com.fillumina.formio.gen;

/**
 * A web address. Carries a format check that the browser and the Java
 * validator both apply; call {@link #pattern(String)} to tighten it.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class UrlComponent extends StringComponent<UrlComponent> {

    private static final String URL_PATTERN = "^(https?|ftp)://\\S+$";

    public UrlComponent(String key) {
        super("url", key, false);
        pattern(URL_PATTERN);
    }
}
