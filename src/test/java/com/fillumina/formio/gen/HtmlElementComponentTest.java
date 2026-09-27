package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class HtmlElementComponentTest {

    @Test
    public void shouldDeclareTheHtmlElementTypeAndCarryNoValue() {
        HtmlElementComponent comp = new HtmlElementComponent("html123");

        assertEquals("htmlelement", comp.toJSONObject().getString("type"));
        assertFalse(comp.toJSONObject().getBoolean("input"));
        assertFalse(comp.isValue());
    }

    @Test
    public void shouldCarryTheMarkupUnderTheContentProperty() {
        HtmlElementComponent comp = new HtmlElementComponent("html123");
        comp.content("<p>hello</p>");

        assertEquals("<p>hello</p>", comp.toJSONObject().getString("content"));
    }

    @Test
    public void shouldChooseTheTagTheMarkupGoesIn() {
        HtmlElementComponent comp = new HtmlElementComponent("html123");
        comp.content("<p>hello</p>").tag("span");

        assertEquals("span", comp.toJSONObject().getString("tag"));
    }
}
