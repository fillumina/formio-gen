package com.fillumina.formio.gen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class UrlComponentTest {

    @Test
    public void shouldDeclareTheUrlType() {
        UrlComponent comp = new UrlComponent("site123");

        assertEquals("url", comp.toJSONObject().getString("type"));
    }

    @Test
    public void shouldAcceptAnHttpUrl() {
        UrlComponent comp = new UrlComponent("site123");

        assertFalse(comp.validate("https://example.com/page").isErrorPresent());
    }

    @Test
    public void shouldRejectSomethingThatIsNotAUrl() {
        UrlComponent comp = new UrlComponent("site123");

        assertEquals(FormError.PATTERN_NOT_MATCHING,
                comp.validate("example.com").getError());
    }

    @Test
    public void shouldPublishThePatternSoTheBrowserAppliesItToo() {
        UrlComponent comp = new UrlComponent("site123");

        String pattern = comp.toJSONObject().getJSONObject("validate").getString("pattern");

        assertTrue(pattern.contains("https"), pattern);
    }
}
