package com.fillumina.formio.gen;

/**
 * A password. The submitted value is protected, so formio does not echo it
 * back when the form is redisplayed.
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class PasswordComponent extends StringComponent<PasswordComponent> {

    public PasswordComponent(String key) {
        super("password", key, false);
        json.put("protected", true);
    }
}
