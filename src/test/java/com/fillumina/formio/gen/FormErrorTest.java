package com.fillumina.formio.gen;

import java.util.Locale;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class FormErrorTest {

    public static void main(String[] args) {
        System.out.println(FormError.NULL_VALUE.getError(Locale.ENGLISH));
        System.out.println(FormError.NULL_VALUE.getError(Locale.ITALY));
    }
    
}
