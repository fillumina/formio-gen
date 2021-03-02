package com.fillumina.formio.gen;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public enum Theme {
    _default, _primary, _success, _info, _warning, _danger;

    @Override
    public String toString() {
        return super.toString().substring(1);
    }
    
}
