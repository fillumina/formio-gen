package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>ncesco Illuminati <fillumina at gmail.com>
 */
public class Container<T extends Container<T>> extends AbstractNonValueComponent<T> {

    public Container(String type, String key) {
        super(type, key);
    }

}

