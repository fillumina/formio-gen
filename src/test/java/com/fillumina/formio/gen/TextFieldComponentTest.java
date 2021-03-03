package com.fillumina.formio.gen;

import java.util.Arrays;
import org.json.JSONArray;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Francesco Illuminati <fillumina at gmail.com>
 */
public class TextFieldComponentTest {

    @Test
    public void shouldAcceptNullText() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        assertFalse(text.validate(null).isErrorPresent());
    }

    @Test
    public void shouldRejectNullText() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.required(true);
        ComponentValue cv = text.validate(null);
        assertEquals(FormError.NULL_VALUE, cv.getError());
        assertTrue(cv.isErrorPresent());
    }

    @Test
    public void shouldRejectInvalidLength() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.minLength(3);
        text.maxLength(10);
        ComponentValue cv = text.validate(null);
        assertEquals(FormError.NULL_VALUE, cv.getError());
        assertTrue(cv.isErrorPresent());
    }

    @Test
    public void shouldAcceptValidLength() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.minLength(3);
        text.maxLength(10);
        ComponentValue cv = text.validate("Hello");
        assertFalse(cv.isErrorPresent());
    }

    @Test
    public void shouldRejectTooShortLength() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.minLength(3);
        text.maxLength(10);
        ComponentValue cv = text.validate("Hi");
        assertEquals(FormError.LENGTH_TOO_SHORT, cv.getError());
        assertTrue(cv.isErrorPresent());
    }

    @Test
    public void shouldRejectTooLongLength() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.minLength(3);
        text.maxLength(10);
        ComponentValue cv = text.validate("Hello world this is me");
        assertEquals(FormError.LENGTH_TOO_LONG, cv.getError());
        assertTrue(cv.isErrorPresent());
    }
    
    @Test
    public void shouldRejectTooFewMultiplicity() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.minItems(3);
        ComponentValue cv = text.validate("Hello world this is me");
        assertEquals(FormError.MULTIPLE_VALUES_TOO_FEW, cv.getError());
        assertTrue(cv.isErrorPresent());
    }
    
    @Test
    public void shouldRejectTooManyMultiplicity() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.maxItems(3);
        ComponentValue cv = text.validate(
                new JSONArray(Arrays.asList("Hello","world", "this", "is", "me")));
        assertEquals(FormError.MULTIPLE_VALUES_TOO_MANY, cv.getError());
        assertTrue(cv.isErrorPresent());
    }
    
    @Test
    public void shouldRejectMultipleFields() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        ComponentValue cv = text.validate(
                new JSONArray(Arrays.asList("Hello","world", "this", "is", "me")));
        assertEquals(FormError.MULTIPLE_VALUES, cv.getError());
        assertTrue(cv.isErrorPresent());
    }
    
    @Test
    public void shouldRejectStringTooShortInMultipleFields() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.multiple(true);
        text.minLength(4);
        ComponentValue cv = text.validate(
                new JSONArray(Arrays.asList("Hello","world", "this", "is", "me")));
        assertEquals(FormError.LENGTH_TOO_SHORT, cv.getError());
        assertTrue(cv.isErrorPresent());
    }
    
    @Test
    public void shouldRejectWrongPattern() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.pattern("[a-z]*");
        ComponentValue cv = text.validate("1234");
        assertEquals(FormError.PATTERN_NOT_MATCHING, cv.getError());
        assertTrue(cv.isErrorPresent());
    }
    
    @Test
    public void shouldAcceptGoodPattern() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.pattern("[a-z]*");
        ComponentValue cv = text.validate("abcdef");
        assertFalse(cv.isErrorPresent());
    }
    
    @Test
    public void shouldCheckExternalValidator() {
        TextFieldComponent text = new TextFieldComponent("txt123");
        text.externalValidator(s -> "error");
        ComponentValue cv = text.validate("1234");
        assertEquals(FormError.EXTERNAL_VALIDATOR, cv.getError());
        assertTrue(cv.isErrorPresent());
    }
    
}
