package com.fillumina.formio.gen;

import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class WysiwygComponentTest {

    @Test
    public void shouldFilterHtmlJavascriptLink() throws ParseException {
        WysiwygComponent str = new WysiwygComponent("wys123", false);
        String healed = str.convert("<a href='javascript:alert(\"hack!\")'>clickme</a>");
        assertEquals("clickme", healed);
    }

    @Test
    public void shouldFilterHtmlLink() throws ParseException {
        WysiwygComponent str = new WysiwygComponent("wys123", false);
        String healed = str.convert("<a href='https://www.google.com'>clickme</a>");
        assertEquals("clickme", healed);
    }

    @Test
    public void shouldNotFilterFormattingTags() throws ParseException {
        WysiwygComponent str = new WysiwygComponent("wys123", false);
        String healed = str.convert("Hello <b>Word</b>!");
        assertEquals("Hello <b>Word</b>!", healed);
    }

    @Test
    public void shouldFilterScriptTags() throws ParseException {
        WysiwygComponent str = new WysiwygComponent("wys123", false);
        String healed = str.convert("Hello <script>alert('fired!')</script>!");
        assertEquals("Hello !", healed);
    }
}
