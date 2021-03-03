package com.fillumina.formio.gen;

import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class StringComponentTest {
    
    @Test
    public void shouldFilterHtmlTags() throws ParseException {
        StringComponent str = new StringComponent("text", "str123");
        String healed = str.convert("<a href='javascript:alert(\"hack!\")'>clickme</a>");
        assertEquals("clickme", healed);
    }
    
    @Test
    public void shouldFilterFormattingTags() throws ParseException {
        StringComponent str = new StringComponent("text", "str123");
        String healed = str.convert("Hello <b>Word</b>!");
        assertEquals("Hello Word!", healed);
    }
    
    @Test
    public void shouldConvertEntities() throws ParseException {
        StringComponent str = new StringComponent("text", "str123");
        String healed = str.convert("Cio&egrave;");
        assertEquals("Cioè", healed);
    }
}
