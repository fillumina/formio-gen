package com.fillumina.formio.gen;

import java.text.ParseException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina@gmail.com>
 */
public class StringComponentTest {

    private final StringComponent<?> str = new StringComponent<>("text", "str123");

    @Test
    public void shouldFilterHtmlTags() throws ParseException {
        String healed = str.convert("<a href='javascript:alert(\"hack!\")'>clickme</a>");

        assertEquals("clickme", healed);
    }

    @Test
    public void shouldFilterFormattingTags() throws ParseException {
        String healed = str.convert("Hello <b>Word</b>!");

        assertEquals("Hello Word!", healed);
    }

    @Test
    public void shouldKeepPlainTextExactlyAsTyped() throws ParseException {
        assertEquals("Cio&egrave;", str.convert("Cio&egrave;"));
        assertEquals("5 > 3", str.convert("5 > 3"));
        assertEquals("a & b", str.convert("a & b"));
    }

    @Test
    public void shouldKeepAnAtSignIntactSoPatternsCanMatch() throws ParseException {
        assertEquals("ada@example.com", str.convert("ada@example.com"));
    }
}
